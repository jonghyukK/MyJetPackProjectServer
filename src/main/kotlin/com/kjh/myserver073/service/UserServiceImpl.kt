package com.kjh.myserver073.service

import com.kjh.myserver073.model.UserModel
import com.kjh.myserver073.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * myserver073
 * Class: UserServiceImpl
 * Created by mac on 2021/07/27.
 *
 * Description:
 */

@Service
class UserServiceImpl constructor(
    @Autowired private val userRepository: UserRepository
): UserService {

    override fun getUserByEmail(email: String): UserModel? {
        return userRepository.findByEmail(email)
    }

    @Transactional
    override fun createUser(userModel: UserModel): UserModel =
        userRepository.save(userModel)
}