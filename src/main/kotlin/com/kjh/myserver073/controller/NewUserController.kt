package com.kjh.myserver073.controller

import com.kjh.myserver073.model.*
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.service.NewUserService
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
class NewUserController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userService: NewUserService

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
    ): ResponseEntity<NewUserResponse> {
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
                .body(NewUserResponse(
                    result   = userValidation == ValidateUser.VALID,
                    errorMsg = userValidation.errorMsg
                ))

        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
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
    ): ResponseEntity<NewUserResponse> {
        try {
            val loginValidation = userService.validateLogin(email, pw)

            return ResponseEntity
                .ok()
                .body(NewUserResponse(
                    result = loginValidation == ValidateUser.VALID,
                    errorMsg = loginValidation.errorMsg
                ))

        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
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
    ): ResponseEntity<NewUserResponse>? {
        try {

            // Other Profile.
            if (targetEmail != null) {
                return ResponseEntity
                    .ok()
                    .body(
                        NewUserResponse(
                            result = true,
                            data = userService.getUserByEmail(targetEmail, myEmail)
                        )
                    )
            }

            // My Profile.
            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
                        result = true,
                        data = userService.getMyUser(myEmail),
                    )
                )

        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
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
                NewUserResponse(
                    result = true,
                    data = userService.updateUser(file, email, nickName, introduce)
                )
            )
    } catch (e: Exception) {
        ResponseEntity
            .ok()
            .body(
                NewUserResponse(
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
                NewUserResponse(
                    result = true,
                    data = followResult
                )
            )

    } catch (e: Exception) {
        ResponseEntity
            .ok()
            .body(
                NewUserResponse(
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
                NewUserResponse(
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
                NewUserResponse(
                    result = false,
                    errorMsg = "업로드가 실패하였습니다."
                )
            )
    }
}


