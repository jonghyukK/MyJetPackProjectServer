package com.kjh.myserver073.service

import com.kjh.myserver073.controller.ValidateUser
import com.kjh.myserver073.model.entity.NewPostModel
import com.kjh.myserver073.model.entity.NewUserModel
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional


@Service
interface NewUserService {

    fun checkExistUser(email: String): ValidateUser

    fun validateLogin(email: String, pw: String): ValidateUser

    @Transactional
    fun updateBookmark(email: String, postId: Int): NewUserModel

    @Transactional
    fun getMyUser(email: String): NewUserModel

    @Transactional
    fun getUserByEmail(email: String, myEmail: String): NewUserModel

    @Transactional
    fun createUser(newUserModel: NewUserModel): NewUserModel

    @Transactional
    fun updateUser(
        file: MultipartFile?,
        email: String,
        nickName: String,
        introduce: String?
    ): NewUserModel

    @Transactional
    fun updateFollowOrNot(myEmail: String, targetEmail: String): NewUserModel

    @Transactional
    fun uploadPost(
        email: String,
        content: String,
        file: List<MultipartFile>,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ): NewUserModel
}