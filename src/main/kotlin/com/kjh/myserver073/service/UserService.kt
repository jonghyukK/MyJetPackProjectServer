package com.kjh.myserver073.service

import com.kjh.myserver073.model.UserModel
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * myserver073
 * Class: UserService
 * Created by mac on 2021/07/27.
 *
 * Description:
 */
@Service
interface UserService {
    @Transactional
    fun getUserByEmail(email: String): UserModel?

    @Transactional
    fun createUser(userModel: UserModel): UserModel

    @Transactional
    fun deleteByUserId(userId: Int)
}