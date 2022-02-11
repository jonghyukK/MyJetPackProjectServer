package com.kjh.myserver073.controller

import com.kjh.myserver073.BASE_IMAGE_URL
import com.kjh.myserver073.IMAGE_SAVE_FOLDER
import com.kjh.myserver073.model.*
import com.kjh.myserver073.model.entity.NewUserModel
import com.kjh.myserver073.service.NewBookMarkService
import com.kjh.myserver073.service.NewUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@RestController
@RequestMapping
class NewUserController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var userService: NewUserService

    @Autowired
    private lateinit var bookmarkService: NewBookMarkService


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
            val user = userService.getMyUser(email)

            var response = NewUserResponse(
                result = user == null,
                errorMsg = if (user == null) null else "이미 가입된 이메일입니다."
            )

            if (user == null) {
                userService.createUser(
                    NewUserModel(
                        userId = null,
                        email = email,
                        pw = pw,
                        nickName = nickName
                    )
                )
            }

            return ResponseEntity
                .ok()
                .body(response)

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
            val user = userService.getMyUser(email)

            val errorText = when {
                user == null -> "가입되지 않은 이메일입니다."
                user.pw != pw -> "비밀번호가 맞지 않습니다."
                else -> null
            }

            val response = NewUserResponse(
                result = errorText == null,
                errorMsg = errorText
            )

            return ResponseEntity
                .ok()
                .body(response)

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
            val myUserData = userService.getMyUser(myEmail)

            // My Profile.
            var userResponse = NewUserResponse(
                result = myUserData != null,
                data = myUserData,
            )

            // Other Profile.
            if (targetEmail != null) {
                val userData = userService.getUserByEmail(targetEmail, myEmail)

                userResponse = NewUserResponse(
                    result = userData != null,
                    data = userData?.copy(
                        isFollowing = myUserData!!.followingList.contains(targetEmail)
                    )
                )
            }

            return ResponseEntity
                .ok()
                .body(userResponse)
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
    ): ResponseEntity<NewUserResponse> {
        try {
            val user = userService.getMyUser(email)!!

            val tempProfileImg = file?.let {
                val profileSavePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER
                if (!File(profileSavePath).exists()) {
                    try {
                        File(profileSavePath).mkdir()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                val originFileName = "${email}_${file.originalFilename}"
                val filePath = "$profileSavePath/$originFileName"
                file.transferTo(File(filePath))

                "$BASE_IMAGE_URL${originFileName}"
            }

            val newUser = userService.createUser(
                user.copy(
                    profileImg = tempProfileImg,
                    nickName   = if (nickName == "null") user.nickName else nickName,
                    introduce  = if (introduce == "null") user.introduce else introduce,
                    posts      = user.posts.map {
                        it.copy(profileImg = tempProfileImg)
                    }
                ))

            return ResponseEntity
                .ok()
                .body(NewUserResponse(
                    result = true,
                    data = newUser
                ))
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(NewUserResponse(
                    result = false,
                    errorMsg = "프로필 변경이 실패하였습니다."
                ))
        }
    }


    /***************************************************
     *
     *  [PUT] User Follow.
     *
     ***************************************************/
    @PutMapping("/user/follow")
    private fun followUser(
        @RequestParam("myEmail"    ) myEmail    : String,
        @RequestParam("targetEmail") targetEmail: String,
    ): ResponseEntity<NewUserResponse> {
        try {
            val myUser = userService.getMyUser(myEmail)!!

            val isAddJob = !myUser.followingList.contains(targetEmail)

            // MY Logic.
            val newFollowingList = if (isAddJob) {
                myUser.followingList + targetEmail
            } else {
                myUser.followingList.filter { it != targetEmail }
            }

            userService.createUser(
                myUser.copy(
                    followingList  = newFollowingList,
                    followingCount = newFollowingList.size
                )
            )

            // Target Logic.
            val targetUser = userService.getUserByEmail(targetEmail)!!

            val newFollowList = if (isAddJob) {
                targetUser.followList + myEmail
            } else {
                targetUser.followList.filter { it != myEmail }
            }

            val newTargetUser = userService.createUser(
                targetUser.copy(
                    followList = newFollowList,
                    followCount = newFollowList.size,
                )
            )

            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
                        result = true,
                        data = newTargetUser.copy(
                            isFollowing = isAddJob
                        ),
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
                        result = false,
                        errorMsg = "팔로우 요청에 실패하였습니다."
                    )
                )
        }
    }
}
