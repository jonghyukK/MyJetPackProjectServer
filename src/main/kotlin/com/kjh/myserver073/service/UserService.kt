package com.kjh.myserver073.service

import com.kjh.myserver073.model.UserModel
import org.springframework.stereotype.Service

/**
 * myserver073
 * Class: UserService
 * Created by mac on 2021/07/27.
 *
 * Description:
 */
@Service
interface UserService {

    fun getUserByEmail(email: String): UserModel?

    fun createUser(userModel: UserModel): UserModel
}