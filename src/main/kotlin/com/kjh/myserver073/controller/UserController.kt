package com.kjh.myserver073.controller

import com.kjh.myserver073.FirebaseCloudMessageService
import com.kjh.myserver073.model.*
import com.kjh.myserver073.service.BookMarkService
import com.kjh.myserver073.service.PostService
import com.kjh.myserver073.service.UserService
import com.kjh.myserver073.utils.savePostData
import com.kjh.myserver073.utils.saveProfileFileGetImagePath
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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

    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var bookMarkService: BookMarkService

    @Autowired
    private lateinit var fcmService: FirebaseCloudMessageService

    private fun failResponse(
        errorMsg: String = "이미 가입된 이메일입니다."
    ) = ResponseEntity
        .ok()
        .body(UserResponse(
            result   = "Failed",
            errorMsg = errorMsg
        ))


    /*******************************************************************
     *
     *  [POST] 회원가입.
     *
     *******************************************************************/
    @PostMapping("/user")
    private fun createUser(
        @RequestParam("email") email: String,
        @RequestParam("pw")    pw   : String,
        @RequestParam("token") token: String,
    ): ResponseEntity<UserResponse> {
        val user = userService.getUserByEmail(email)

        if (user != null)
            return failResponse()

        userService.createUser(UserModel(
            userId      = null,
            email       = email,
            pw          = pw,
            token       = token
        ))

        return ResponseEntity
            .ok()
            .body(UserResponse(
                result = "Success",
                errorMsg = "",
            ))
    }

    /***************************************************
     *
     *  [GET] 이메일 중복 체크.
     *
     ***************************************************/
    @GetMapping("/user/duplicate")
    private fun duplicateCheckUser(
        @RequestParam(value = "email") email: String
    ): ResponseEntity<UserResponse> {
        val user = userService.getUserByEmail(email)

        return if (user == null)
            ResponseEntity
                .ok()
                .body(UserResponse(
                    result = "Success",
                    errorMsg = "",
                ))
            else failResponse()
    }

    /***************************************************
     *
     *  [GET] 로그인.
     *
     ***************************************************/
    @GetMapping("/user/login")
    private fun reqLogin(
        @RequestParam(value = "email") email: String,
        @RequestParam(value = "pw")    pw   : String,
        @RequestParam(value = "token") token: String,
    ): ResponseEntity<UserResponse> {

        val user = userService.getUserByEmail(email)

        return when {
            user == null  -> failResponse("가입되지 않은 이메일입니다.")
            user.pw != pw -> failResponse("비밀번호가 맞지 않습니다.")
            else -> {
                if (user.token != token) {
                    userService.createUser(user.copy(token = token))
                }

                ResponseEntity
                    .ok()
                    .body(
                        UserResponse(
                            result = "Success",
                            errorMsg = "",
                        )
                    )
            }
        }
    }


    /***************************************************
     *
     *  [GET] 유저 정보 by Email.
     *
     ***************************************************/
    @GetMapping("/user")
    fun getUser(
        @RequestParam(value = "email") email: String
    ): ResponseEntity<Any?> {
        val user = userService.getUserByEmail(email)!!

        return ResponseEntity
            .ok()
            .body(toUserVo(user))
    }


    /***************************************************
     *
     * [PUT] Update User Bookmark List.
     *
     ***************************************************/
    @PutMapping("/user/bookmark")
    private fun updateBookMarkUser(
        @RequestParam("email"    ) email    : String,
        @RequestParam("postId"   ) postId   : Int,
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<Any> {
        val prevUser = userService.getUserByEmail(email)!!

        val bookMarkItem = prevUser.bookMarks.find { it.postId == postId }

        if (bookMarkItem == null) {
            val updatedUser = with(prevUser) {
                bookMarks += BookMarkModel(
                    bookmarkId  = 1000,
                    userId      = prevUser.userId!!,
                    postId      = postId,
                    placeName   = placeName
                )
                copy(bookMarks = bookMarks).run {
                    userService.createUser(this)
                }
            }

            fcmService.sendMessageTo(
                updatedUser.token,
                placeName,
                "북마크에 $placeName 추가되었습니다.")

            return ResponseEntity
                .ok()
                .body(toUserVo(updatedUser))
        } else {

            val updateUser = prevUser.copy(
                bookMarks = prevUser.bookMarks.filter { it.postId != postId }.toMutableList()
            ).run {
                userService.createUser(this)
            }

            bookMarkService.deleteByBookmarkId(bookMarkItem.bookmarkId!!)

            return ResponseEntity
                .ok()
                .body(toUserVo(updateUser))
        }
    }



    /***************************************************
     *
     * [PUT] Update User Profile.
     *
     ***************************************************/
    @PutMapping("/user")
    private fun updateUser(
        @RequestParam("file" ) file : MultipartFile,
        @RequestParam("email") email: String
    ): ResponseEntity<Any> {
        try {
            val prevUser = userService.getUserByEmail(email)!!

            val uploadedProfileImg = saveProfileFileGetImagePath(email, file)

            val updatedUserModel = prevUser.copy(
                profileImg = uploadedProfileImg,
                posts = prevUser.posts.map {
                    it.copy(profileImg = uploadedProfileImg)
                }.toMutableList()
            )

            val user = userService.createUser(updatedUserModel)

            return ResponseEntity
                .ok()
                .body(toUserVo(user))
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(e.message)
        }
    }

    /***************************************************
     *
     *  [POST] 게시물 업로드.
     *
     ***************************************************/
    @PostMapping("/user/upload")
    private fun uploadPost(
        @RequestParam("email"               ) email         : String,
        @RequestParam("content"             ) content       : String,
        @RequestPart("file"                 ) file          : List<MultipartFile>,
        @RequestParam("address_name"        ) address_name  : String,
        @RequestParam("category_group_code" ) category_group_code: String,
        @RequestParam("category_group_name" ) category_group_name: String,
        @RequestParam("category_name"       ) category_name : String,
        @RequestParam("phone"               ) phone         : String,
        @RequestParam("place_name"          ) place_name    : String,
        @RequestParam("place_url"           ) place_url     : String,
        @RequestParam("road_address_name"   ) road_address_name: String,
        @RequestParam("x"                   ) x             : String,
        @RequestParam("y"                   ) y             : String,
    ): ResponseEntity<Any> {
        try {
            val prevUser = userService.getUserByEmail(email)!!

            val savedPostData = savePostData(
                email,
                prevUser.profileImg,
                content,
                file,
                address_name,
                category_group_code,
                category_group_name,
                category_name,
                phone,
                place_name,
                place_url,
                road_address_name,
                x,
                y
            )

            val updateUser = with(prevUser) {
                posts += savedPostData
                copy(
                    posts = posts,
                    postCount = posts.size
                )
            }.run {
                userService.createUser(this)
            }

            return ResponseEntity
                .ok()
                .body(toUserVo(updateUser))

        } catch (e: Exception) {
            e.printStackTrace()

            return ResponseEntity
                .ok()
                .body(UserResponse(
                    result = "Failed",
                    errorMsg = "Failed Upload Image"
                ))
        }
    }


    fun toUserVo(
        userData: UserModel,
    ): UserVo {
        val postListToMap =
            if (userData.posts.isNotEmpty())
                mapOf("전체" to userData.posts.reversed()) + userData.posts.reversed()
                    .groupBy({ it.cityCategory }, { it })
            else
                mapOf()

        val bookMarkList = userData.bookMarks.map {
            postService.findByPostId(it.postId)
        }

        return UserVo(
            userData.userId,
            userData.email,
            userData.pw,
            userData.postCount,
            userData.followingCount,
            userData.followCount,
            userData.profileImg,
            bookMarkList,
            postListToMap
        )
    }
}