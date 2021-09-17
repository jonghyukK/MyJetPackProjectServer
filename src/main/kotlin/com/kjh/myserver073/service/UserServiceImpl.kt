package com.kjh.myserver073.service

import com.kjh.myserver073.model.UserModel
import com.kjh.myserver073.repository.BookMarkRepository
import com.kjh.myserver073.repository.PostRepository
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
    @Autowired private val userRepository: UserRepository,
    @Autowired private val bookMarkRepository: BookMarkRepository
): UserService {

    @Transactional
    override fun getUserByEmail(email: String): UserModel? {
        val user = userRepository.findByEmail(email)

        user?.let {
            val bookMarks = bookMarkRepository.findByUserId(user.userId!!).toMutableList()
            return user.copy(bookMarks = bookMarks)
        }

        return user
    }

    @Transactional
    override fun createUser(userModel: UserModel): UserModel =
        userRepository.save(userModel)

    @Transactional
    override fun deleteByUserId(userId: Int) {
        userRepository.deleteByUserId(userId)
    }


}