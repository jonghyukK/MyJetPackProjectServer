package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewUserModel
import com.kjh.myserver073.repository.BookMarkRepository
import com.kjh.myserver073.repository.PostRepository
import com.kjh.myserver073.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class NewUserServiceImpl constructor(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val newBookMarkRepository: BookMarkRepository
): NewUserService {

    @Transactional
    override fun getMyUser(email: String): NewUserModel? {
        val user = userRepository.findUserByEmail(email)

        user?.let { userModel ->
            val bookmarks = newBookMarkRepository.findByUserId(userModel.userId!!)

            val convertPosts = userModel.posts.map { postItem ->
                if (bookmarks.find { it.placeName == postItem.placeName } != null) {
                    postItem.copy(isBookmarked = true)
                } else {
                    postItem.copy(isBookmarked = false)
                }
            }

            return userModel.copy(
                bookMarks = bookmarks,
                posts = convertPosts.reversed()
            )
        }

        return user
    }

    @Transactional
    override fun getUserByEmail(email: String, myEmail: String?): NewUserModel? {
        val user = userRepository.findUserByEmail(email)

        if (myEmail != null) {
            val myUser = userRepository.findUserByEmail(myEmail)

            myUser?.let { myUserModel ->
                val bookmarks = newBookMarkRepository.findByUserId(myUserModel.userId!!)

                val convertPosts = user!!.posts.map { postItem ->
                    if (bookmarks.find { it.placeName == postItem.placeName } != null) {
                        postItem.copy(isBookmarked = true)
                    } else {
                        postItem.copy(isBookmarked = false)
                    }
                }

                return user.copy(
                    bookMarks = bookmarks,
                    posts = convertPosts.reversed()
                )
            }
        }

        return user
    }

    override fun createUser(newUserModel: NewUserModel) =
        userRepository.save(newUserModel)
}