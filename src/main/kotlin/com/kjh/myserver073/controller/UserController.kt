package com.kjh.myserver073.controller

import com.kjh.myserver073.model.UserModel
import com.kjh.myserver073.model.UserResponse
import com.kjh.myserver073.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * myserver073
 * Class: UserController
 * Created by mac on 2021/07/27.
 *
 * Description:
 */
@RestController
@RequestMapping
class UserController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/user")
    private fun getUserByEmail(@RequestParam(value = "email") email: String): ResponseEntity<Any?> {
        return ResponseEntity
            .ok()
            .body(userService.getUserByEmail(email))
    }

    @PostMapping("/user")
    private fun postUser(@RequestBody userModel: UserModel): ResponseEntity<Any> {
        logger.debug("""
            postUser() : $userModel
        """.trimIndent())

        val str = userService.getUserByEmail(userModel.email)

        logger.info("Test TEst Test    : $str")

        if (str != null) {
            return ResponseEntity
                .ok()
                .body(UserResponse(
                    result = "Failed",
                    errorMsg = "이미 가입된 이메일 입니다."
                ))
        }

        userService.createUser(userModel)
        return ResponseEntity
            .ok()
            .body(UserResponse(
                result = "Success",
                errorMsg = "",
            ))
    }
}