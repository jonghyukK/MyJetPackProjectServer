package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewUserModel
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
interface NewUserService {

    @Transactional
    fun getMyUser(email: String): NewUserModel?

    @Transactional
    fun getUserByEmail(email: String, myEmail: String? = null): NewUserModel?

    @Transactional
    fun createUser(newUserModel: NewUserModel): NewUserModel
}