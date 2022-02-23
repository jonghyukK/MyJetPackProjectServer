package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.User
import org.springframework.data.repository.CrudRepository

/**
 * myserver073
 * Class: UserRepository
 * Created by mac on 2021/07/27.
 *
 * Description:
 */

interface UserRepository: CrudRepository<User, Int> {

    fun findUserByEmail(email: String): User?
}