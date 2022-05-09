package com.kjh.myserver073.controller

import com.kjh.myserver073.model.*
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.vo.LoginVo
import com.kjh.myserver073.model.vo.SignUpVo
import com.kjh.myserver073.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

enum class ValidateUser(val errorMsg: String?) {
    VALID(null),
    EXIST_EMAIL("이미 가입된 이메일입니다."),
    NOT_EXIST_EMAIL("가입되지 않은 이메일입니다."),
    NOT_MATCH_PW("비밀번호가 맞지 않습니다.");
}

@RestController
@RequestMapping
class UserController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userService: UserService

    /*******************************************************************
     *
     *  [POST] 회원가입.
     *
     *******************************************************************/
    @PostMapping("/user")
    private fun createUser(
            @RequestParam("email")    email   : String,
            @RequestParam("pw")       pw      : String,
            @RequestParam("nickName") nickName: String,
    ): ResponseEntity<UserResponse> {
        try {
            val userValidation = userService.checkExistUser(email)

            if (userValidation == ValidateUser.VALID) {
                userService.createUser(
                    User(
                        userId   = null,
                        email    = email,
                        pw       = pw,
                        nickName = nickName
                    )
                )
            }

            return ResponseEntity
                .ok()
                .body(UserResponse(
                    result = true,
                    data = SignUpVo(
                        isSuccess = userValidation == ValidateUser.VALID,
                        signUpErrorMsg = userValidation.errorMsg
                    )
                ))

        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    UserResponse(
                        result = false,
                        errorMsg = "Error Create User."
                    )
                )
        }
    }

    /*******************************************************************
     *
     *  [GET] 로그인.
     *
     *******************************************************************/
    @GetMapping("/user/login")
    private fun requestLogin(
            @RequestParam("email") email   : String,
            @RequestParam("pw")    pw      : String,
    ): ResponseEntity<UserResponse> {
        try {
            val loginValidation = userService.validateLogin(email, pw)

            return ResponseEntity
                .ok()
                .body(UserResponse(
                    result = true,
                    data = LoginVo(
                        isSuccess     = loginValidation == ValidateUser.VALID,
                        loginErrorMsg = loginValidation.errorMsg
                    )
                ))

        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    UserResponse(
                        result = false,
                        errorMsg = "Error Request Login."
                    )
                )
        }
    }

    /*******************************************************************
     *
     *  [GET] 유저 정보 By Email.
     *
     *******************************************************************/
    @GetMapping("/user")
    fun getUser(
        @RequestParam("myEmail"    ) myEmail    : String,
        @RequestParam("targetEmail") targetEmail: String?
    ): ResponseEntity<UserResponse>? {
        try {

            // Other Profile.
            if (targetEmail != null) {
                return ResponseEntity
                    .ok()
                    .body(
                        UserResponse(
                            result = true,
                            data = userService.getUserByEmail(targetEmail, myEmail)
                        )
                    )
            }

            // My Profile.
            return ResponseEntity
                .ok()
                .body(
                    UserResponse(
                        result = true,
                        data = userService.getMyUser(myEmail),
                    )
                )

        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    UserResponse(
                        result = false,
                        errorMsg = "Error Get User."
                    )
                )
        }
    }

    /***************************************************
     *
     *  [PUT] User Update.
     *
     ***************************************************/
    @PutMapping("/user/update")
    private fun updateUser(
        @RequestPart(value = "file", required = false) file: MultipartFile?,
        @RequestParam("email"    ) email    : String,
        @RequestParam("nickName" ) nickName : String,
        @RequestParam("introduce") introduce: String?
    ) = try {
        ResponseEntity
            .ok()
            .body(
                UserResponse(
                    result = true,
                    data = userService.updateUser(file, email, nickName, introduce)
                )
            )
    } catch (e: Exception) {
        ResponseEntity
            .ok()
            .body(
                UserResponse(
                    result = false,
                    errorMsg = "프로필 변경이 실패하였습니다."
                )
            )
    }


    /***************************************************
     *
     *  [PUT] User Follow.
     *
     ***************************************************/
    @PutMapping("/user/follow")
    private fun followUser(
        @RequestParam("myEmail"    ) myEmail    : String,
        @RequestParam("targetEmail") targetEmail: String
    ) = try {
        val followResult = userService.updateFollowOrNot(myEmail, targetEmail)

        ResponseEntity
            .ok()
            .body(
                UserResponse(
                    result = true,
                    data = followResult
                )
            )

    } catch (e: Exception) {
        ResponseEntity
            .ok()
            .body(
                UserResponse(
                    result = false,
                    errorMsg = "팔로우 요청에 실패하였습니다."
                )
            )
    }

    /***************************************************
     *
     *  [POST] 게시물 업로드.
     *
     ***************************************************/
    @PostMapping("/user/upload")
    fun uploadPost(
        @RequestParam("email"       ) email        : String,
        @RequestParam("content"     ) content      : String,
        @RequestPart("file"         ) file         : List<MultipartFile>,
        @RequestParam("placeName"   ) placeName    : String,
        @RequestParam("placeAddress") placeAddress : String,
        @RequestParam("placeRoadAddress") placeRoadAddress : String,
        @RequestParam("x") x : String,
        @RequestParam("y") y : String,
    ) = try {
        ResponseEntity
            .ok()
            .body(
                UserResponse(
                    result = true,
                    data = userService.uploadPost(
                        email, content, file, placeName, placeAddress, placeRoadAddress, x, y
                    )
                )
            )
    } catch (e: Exception) {
        e.printStackTrace()

        ResponseEntity
            .ok()
            .body(
                UserResponse(
                    result = false,
                    errorMsg = "업로드가 실패하였습니다."
                )
            )
    }
}


