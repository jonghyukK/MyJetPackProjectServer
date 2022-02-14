package com.kjh.myserver073.service

import com.kjh.myserver073.BASE_IMAGE_URL
import com.kjh.myserver073.IMAGE_SAVE_FOLDER
import com.kjh.myserver073.controller.ValidateUser
import com.kjh.myserver073.model.entity.NewPostModel
import com.kjh.myserver073.model.entity.NewUserModel
import com.kjh.myserver073.repository.PostRepository
import com.kjh.myserver073.repository.UserRepository
import com.kjh.myserver073.utils.TimeFormatter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.transaction.Transactional


@Service
class NewUserServiceImpl constructor(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val postRepository: PostRepository
): NewUserService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun checkExistUser(email: String) =
        when (userRepository.findUserByEmail(email)) {
            null -> ValidateUser.VALID
            else -> ValidateUser.EXIST_EMAIL
        }

    override fun validateLogin(email: String, pw: String): ValidateUser {
        val user = userRepository.findUserByEmail(email)

        return when {
            user == null -> ValidateUser.NOT_EXIST_EMAIL
            user.pw != pw -> ValidateUser.NOT_MATCH_PW
            else -> ValidateUser.VALID
        }
    }

    @Transactional
    override fun getMyUser(email: String): NewUserModel {
        val user = userRepository.findUserByEmail(email)!!

        val convertPosts = postRepository.findAllByUserId(user.userId!!).map { post ->
            post.copy(
                isBookmarked = user.bookMarks.find { it.postId == post.postId } != null
            )
        }

        return user.copy(
            posts = convertPosts.reversed()
        )
    }

    @Transactional
    override fun getUserByEmail(email: String, myEmail: String): NewUserModel {
        val targetUser = userRepository.findUserByEmail(email)!!
        val myUser     = getMyUser(myEmail)

        val convertTargetUserPosts = postRepository.findAllByUserId(targetUser.userId!!).map { post ->
            post.copy(
                isBookmarked = myUser.bookMarks.find { it.postId == post.postId } != null
            )
        }

        return targetUser.copy(
            posts       = convertTargetUserPosts,
            isFollowing = myUser.followingList.contains(targetUser.email)
        )
    }

    @Transactional
    override fun updateBookmark(email: String, postId: Int): NewUserModel {
        val postData = postRepository.findByPostId(postId)
        val userData = userRepository.findUserByEmail(email)!!

        val newBookmarks = if (userData.bookMarks.find { it.postId == postId } == null) {
            userData.bookMarks + postData
        } else {
            userData.bookMarks.filter { it.postId != postId }
        }

        return createUser(
            userData.copy(
                bookMarks = newBookmarks
            )
        )
    }

    @Transactional
    override fun createUser(newUserModel: NewUserModel) = userRepository.save(newUserModel)

    @Transactional
    override fun updateUser(
        file        : MultipartFile?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): NewUserModel {
        val userData = getMyUser(email)

        val tempProfileImg = file?.let {
            val profileSavePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER
            if (!File(profileSavePath).exists()) {
                try {
                    File(profileSavePath).mkdir()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            val originFileName = "${email}_${file.originalFilename}"
            val filePath = "$profileSavePath/$originFileName"
            file.transferTo(File(filePath))

            "$BASE_IMAGE_URL${originFileName}"
        }

        return createUser(
            userData.copy(
                profileImg = tempProfileImg,
                nickName   = if (nickName == "null") userData.nickName else nickName,
                introduce  = if (introduce == "null") userData.introduce else introduce,
                posts      = userData.posts.map {
                    it.copy(profileImg = tempProfileImg)
                }
            )
        )
    }

    @Transactional
    override fun updateFollowOrNot(myEmail: String, targetEmail: String): NewUserModel {
        val myUserData = getMyUser(myEmail)
        val isFollowed = myUserData.followingList.contains(targetEmail)

        // My Logic.
        val newFollowingList = if (isFollowed) {
            myUserData.followingList.filter { it != targetEmail }
        } else {
            myUserData.followingList + targetEmail
        }

        createUser(myUserData.copy(
            followingList = newFollowingList,
            followingCount = newFollowingList.size
        ))

        // Target Logic.
        val targetUser = getUserByEmail(targetEmail, myEmail)
        val newFollowList = if (isFollowed) {
            targetUser.followList.filter { it != myEmail }
        } else {
            targetUser.followList + myEmail
        }

        return createUser(targetUser.copy(
            followList  = newFollowList,
            followCount = newFollowList.size
        ))
    }

    override fun uploadPost(
        email: String,
        content: String,
        file: List<MultipartFile>,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ): NewUserModel {
        val user = getMyUser(email)

        val imgUrls = mutableListOf<String>()
        val savePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER

        if (!File(savePath).exists()) {
            try {
                File(savePath).mkdir()
            } catch (e: Exception) {
                e.stackTrace
            }
        }

        for (item in file) {
            val originFileName = item.originalFilename ?: "Empty"
            val filePath = "$savePath/$originFileName"
            item.transferTo(File(filePath))

            imgUrls.add("$BASE_IMAGE_URL${item.originalFilename}")
        }

        val newPostItem = NewPostModel(
            postId      = null,
            email       = email,
            nickName    = user.nickName,
            content     = content,
            cityName    = placeAddress.split(" ")[0],
            subCityName = placeAddress.split(" ")[1],
            placeName   = placeName,
            placeAddress      = placeAddress,
            placeRoadAddress = placeRoadAddress,
            x = x,
            y = y,
            profileImg  = user.profileImg,
            createdDate = TimeFormatter.makeDateTimeFormat(),
            imageUrl    = imgUrls,
        )

        return createUser(
            user.copy(
                posts     = user.posts + newPostItem,
                postCount = user.posts.size + 1
            )
        )
    }
}