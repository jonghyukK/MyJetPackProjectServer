package com.kjh.myserver073.controller

import com.kjh.myserver073.BASE_IMAGE_URL
import com.kjh.myserver073.IMAGE_SAVE_FOLDER
import com.kjh.myserver073.model.*
import com.kjh.myserver073.service.UserService
import com.kjh.myserver073.utils.LocationUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

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
     *  회원가입.
     *
     *******************************************************************/
    @PostMapping("/user")
    private fun createUser(
        @RequestParam("email") email: String,
        @RequestParam("pw")    pw   : String,
    ): ResponseEntity<UserResponse> {
        val user = userService.getUserByEmail(email)

        if (user != null)
            return failResponse()

        userService.createUser(UserModel(
            userId      = null,
            email       = email,
            pw          = pw,
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
     *  이메일 중복 체크.
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
     *  유저 정보 by Email.
     *
     ***************************************************/
    @GetMapping("/user")
    fun getUser(
        @RequestParam(value = "email") email: String
    ): ResponseEntity<Any?> {
        val user = userService.getUserByEmail(email)!!

        val convertPosts = user.posts.groupBy({ it.cityCategory }, { it })
            .plus("전체" to user.posts)

        val userVo = UserVo(
            user.userId,
            user.email,
            user.pw,
            user.postCount,
            user.followingCount,
            user.followCount,
            user.profileImg,
            convertPosts
        )

        return ResponseEntity
            .ok()
            .body(userVo)
    }

    /***************************************************
     *
     *  로그인.
     *
     ***************************************************/
    @GetMapping("/user/login")
    private fun reqLogin(
        @RequestParam(value = "email") email: String,
        @RequestParam(value = "pw")    pw   : String,
    ): ResponseEntity<UserResponse> {

        val user = userService.getUserByEmail(email)

        return when {
            user == null  -> failResponse("가입되지 않은 이메일입니다.")
            user.pw != pw -> failResponse("비밀번호가 맞지 않습니다.")
            else -> ResponseEntity
                .ok()
                .body(
                    UserResponse(
                        result = "Success",
                        errorMsg = "",
                    )
                )
        }
    }

    @DeleteMapping("/user")
    private fun deleteUser(
        @RequestParam(value = "postId") postId: Int
    ): ResponseEntity<Any> {
//        val user = userService.getUserByEmail("saz300@naver.com")
        val result = userService.deleteByUserId(postId)

        return ResponseEntity
            .ok()
            .body("test")
    }


    /***************************************************
     *
     *  게시물 업로드.
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
            val savedPostData = savePostData(
                email,
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

            val updateUser =
                with(userService.getUserByEmail(email)!!) {
                    posts += savedPostData
                    copy(
                        posts = posts,
                        postCount = posts.size
                    )
                }.run {
                    userService.createUser(this)
                }

            val convertPosts = updateUser.posts.groupBy({ it.cityCategory }, { it })
                .plus("전체" to updateUser.posts)

            val userVo = UserVo(
                updateUser.userId,
                updateUser.email,
                updateUser.pw,
                updateUser.postCount,
                updateUser.followingCount,
                updateUser.followCount,
                updateUser.profileImg,
                convertPosts
            )

            return ResponseEntity
                .ok()
                .body(userVo)

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

    private fun savePostData(
        email       : String,
        content     : String,
        files       : List<MultipartFile>,
        address_name: String,
        category_group_code: String,
        category_group_name: String,
        category_name: String,
        phone       : String,
        place_name  : String,
        place_url   : String,
        road_address_name: String,
        x           : String,
        y           : String,
    ): PostModel {
        val imgUrls = mutableListOf<String>()
        val savePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER

        if (!File(savePath).exists()) {
            try {
                File(savePath).mkdir()
            } catch (e: Exception) {
                e.stackTrace
            }
        }

        for (file in files) {
            val originFileName = file.originalFilename ?: "Empty"
            val filePath = "$savePath/$originFileName"
            file.transferTo(File(filePath))

            imgUrls.add("$BASE_IMAGE_URL${file.originalFilename}")
        }

        return PostModel(
            postId = null,
            cityCategory = LocationUtil.makeCityCategory(address_name),
            imageUrl = imgUrls,
            email    = email,
            content  = content,
            address_name = address_name,
            category_group_code = category_group_code,
            category_group_name = category_group_name,
            category_name = category_name,
            phone = phone,
            place_name = place_name,
            place_url = place_url,
            road_address_name = road_address_name,
            x = x,
            y = y,
        )
    }
}