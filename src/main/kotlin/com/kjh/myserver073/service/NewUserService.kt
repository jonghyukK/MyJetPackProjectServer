package com.kjh.myserver073.service

import com.kjh.myserver073.controller.ValidateUser
import com.kjh.myserver073.model.vo.PostVo
import com.kjh.myserver073.model.vo.UserVo
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.vo.FollowVo
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional


@Service
interface NewUserService {

    fun checkExistUser(email: String): ValidateUser

    fun validateLogin(email: String, pw: String): ValidateUser

    @Transactional
    fun getMyUser(email: String): UserVo

    @Transactional
    fun getUserByEmail(email: String, myEmail: String): UserVo

    @Transactional
    fun updateBookmark(email: String, postId: Int): List<PostVo>

    @Transactional
    fun createUser(user: User): User

    @Transactional
    fun updateUser(
        file: MultipartFile?,
        email: String,
        nickName: String,
        introduce: String?
    ): UserVo

    @Transactional
    fun updateFollowOrNot(myEmail: String, targetEmail: String): FollowVo

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
    ): UserVo
}