package com.kjh.myserver073.service

import com.kjh.myserver073.controller.ValidateUser
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.model.UserFollowModel
import com.kjh.myserver073.model.vo.UserAndPostsAndBookmarks
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional


@Service
interface UserService {

    fun checkExistUser(email: String): ValidateUser

    fun validateLogin(email: String, pw: String): ValidateUser

    @Transactional
    fun createUser(
        user: User
    ): User

    @Transactional
    fun getMyUser(
        email: String
    ): UserAndPostsAndBookmarks

    @Transactional
    fun getUserByEmail(
        email   : String,
        myEmail : String
    ): UserAndPostsAndBookmarks

    @Transactional
    fun updateUser(
        file        : MultipartFile?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): UserAndPostsAndBookmarks

    @Transactional
    fun updateFollowOrNot(
        myEmail     : String,
        targetEmail : String
    ): UserFollowModel

    @Transactional
    fun uploadPost(
        email           : String,
        content         : String,
        file            : List<MultipartFile>,
        placeName       : String,
        placeAddress    : String,
        placeRoadAddress: String,
        x               : String,
        y               : String
    ): UserAndPostsAndBookmarks
}