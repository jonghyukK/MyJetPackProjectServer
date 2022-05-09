package com.kjh.myserver073.service

import com.kjh.myserver073.BASE_IMAGE_URL
import com.kjh.myserver073.IMAGE_SAVE_FOLDER
import com.kjh.myserver073.controller.ValidateUser
import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.vo.UserVo
import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.vo.FollowVo
import com.kjh.myserver073.repository.BookmarkRepository
import com.kjh.myserver073.repository.PlaceRepository
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
class UserServiceImpl constructor(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val postRepository: PostRepository,
        @Autowired private val bookmarkRepository: BookmarkRepository,
        @Autowired private val placeRepository: PlaceRepository
): UserService {
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
    override fun getMyUser(email: String): UserVo {
        val user  = userRepository.findUserByEmail(email)!!
        val posts = postRepository.findAllByUserUserIdOrderByCreatedAtDesc(user.userId!!)
        val bookmarks = bookmarkRepository.findAllByUserId(user.userId).map { bookmark ->
            postRepository.findById(bookmark.postId).get()
        }

        return Mappers.makeUserVoWithPosts(user, posts, bookmarks)
    }

    @Transactional
    override fun getUserByEmail(email: String, myEmail: String): UserVo {
        val targetUser = userRepository.findUserByEmail(email)!!
        val myUser     = userRepository.findUserByEmail(myEmail)!!
        val targetUserPosts = postRepository.findAllByUserUserIdOrderByCreatedAtDesc(targetUser.userId!!)

        val bookmarks = bookmarkRepository.findAllByUserId(myUser.userId!!).map { bookmark ->
            postRepository.findById(bookmark.postId).get()
        }

        return Mappers.makeUserVoWithPosts(
            targetUser.copy(isFollowing = myUser.followingList.contains(targetUser.email)),
            targetUserPosts,
            bookmarks
        )
    }

    @Transactional
    override fun createUser(user: User) = userRepository.save(user)

    @Transactional
    override fun updateUser(
        file        : MultipartFile?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): UserVo {
        val user = userRepository.findUserByEmail(email)!!

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

        val convertUser = createUser(
            user.copy(
                profileImg = tempProfileImg,
                nickName   = if (nickName == "null") user.nickName else nickName,
                introduce  = if (introduce == "null") user.introduce else introduce
            )
        )

        val posts = postRepository.findAllByUserUserIdOrderByCreatedAtDesc(user.userId!!)
        val bookmarks = bookmarkRepository.findAllByUserId(user.userId).map {
            postRepository.findById(it.postId).get()
        }

        return Mappers.makeUserVoWithPosts(convertUser, posts, bookmarks)
    }

    @Transactional
    override fun updateFollowOrNot(myEmail: String, targetEmail: String): FollowVo {
        val myUserData = userRepository.findUserByEmail(myEmail)!!
        val isFollowed = myUserData.followingList.contains(targetEmail)

        // My Logic.
        val newFollowingList = if (isFollowed) {
            myUserData.followingList.filter { it != targetEmail }
        } else {
            myUserData.followingList + targetEmail
        }

        val updatedMyUser = createUser(myUserData.copy(
            followingList  = newFollowingList,
            followingCount = newFollowingList.size
        ))

        // Target Logic.
        val targetUser = userRepository.findUserByEmail(targetEmail)!!
        val newFollowList = if (isFollowed) {
            targetUser.followList.filter { it != myEmail }
        } else {
            targetUser.followList + myEmail
        }

        val updatedTargetUser = createUser(targetUser.copy(
            followList  = newFollowList,
            followCount = newFollowList.size
        ))

        val myProfile = Mappers.makeUserVoWithPosts(
            updatedMyUser,
            postRepository.findAllByUserUserIdOrderByCreatedAtDesc(myUserData.userId!!),
            bookmarkRepository.findAllByUserId(myUserData.userId).map {
                postRepository.findById(it.postId).get()
            }
        )

        val targetProfile = Mappers.makeUserVoWithPosts(
            updatedTargetUser.copy(isFollowing = !isFollowed),
            postRepository.findAllByUserUserIdOrderByCreatedAtDesc(targetUser.userId!!),
            bookmarkRepository.findAllByUserId(myUserData.userId).map {
                postRepository.findById(it.postId).get()
            }
        )

        return FollowVo(
            myProfile = myProfile,
            targetProfile = targetProfile
        )
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
    ): UserVo {
        val user = userRepository.findUserByEmail(email)!!

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

        val existPlace = placeRepository.findByPlaceName(placeName)
        val updatedPlace: Place

        if (existPlace == null) {
            updatedPlace = placeRepository.save(Place(
                placeId     = placeName,
                cityName    = placeAddress.split(" ")[0],
                subCityName = placeAddress.split(" ")[1],
                placeName   = placeName,
                placeAddress     = placeAddress,
                placeRoadAddress = placeRoadAddress,
                x = x,
                y = y,
                uploadCount = 1,
                placeImg = imgUrls[0],
            ))
        } else {
            updatedPlace = placeRepository.save(
                existPlace.copy(
                    uploadCount = existPlace.uploadCount + 1,
                    placeImg = imgUrls[0]
                )
            )
        }

        val newPostItem = Post(
            postId      = null,
            content     = content,
            createdDate = TimeFormatter.makeDateTimeFormat(),
            imageUrl    = imgUrls,
            user        = user,
            place       = updatedPlace
        )

        postRepository.save(newPostItem)

        val posts = postRepository.findAllByUserUserIdOrderByCreatedAtDesc(user.userId!!)
        val newUser = createUser(user.copy(postCount = posts.size))
        val bookmarks = bookmarkRepository.findAllByUserId(user.userId).map {
            postRepository.findById(it.postId).get()
        }


        return Mappers.makeUserVoWithPosts(newUser, posts, bookmarks)
    }
}