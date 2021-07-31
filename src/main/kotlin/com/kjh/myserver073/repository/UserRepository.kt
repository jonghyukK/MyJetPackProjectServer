package com.kjh.myserver073.repository

import com.kjh.myserver073.model.UserModel
import org.springframework.data.repository.CrudRepository

/**
 * myserver073
 * Class: UserRepository
 * Created by mac on 2021/07/27.
 *
 * Description:
 */
interface UserRepository: CrudRepository<UserModel, String> {

    fun findByEmail(email: String): UserModel?
}