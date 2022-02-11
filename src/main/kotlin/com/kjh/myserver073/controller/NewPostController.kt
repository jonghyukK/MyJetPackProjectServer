package com.kjh.myserver073.controller

import com.kjh.myserver073.BASE_IMAGE_URL
import com.kjh.myserver073.IMAGE_SAVE_FOLDER
import com.kjh.myserver073.model.NewUserResponse
import com.kjh.myserver073.model.PlaceModel
import com.kjh.myserver073.model.PlaceResponse
import com.kjh.myserver073.model.entity.NewPostModel
import com.kjh.myserver073.service.NewBookMarkService
import com.kjh.myserver073.service.NewPostService
import com.kjh.myserver073.service.NewUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


@RestController
@RequestMapping
class NewPostController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var postService: NewPostService

    @Autowired
    private lateinit var userService: NewUserService

    @Autowired
    private lateinit var bookmarkService: NewBookMarkService

    /***************************************************
     *
     *  [POST] 게시물 업로드.
     *
     ***************************************************/
    @PostMapping("/post/upload")
    fun uploadPost(
        @RequestParam("email"       ) email        : String,
        @RequestParam("content"     ) content      : String,
        @RequestPart("file"         ) file         : List<MultipartFile>,
        @RequestParam("placeName"   ) placeName    : String,
        @RequestParam("placeAddress") placeAddress : String,
        @RequestParam("placeRoadAddress") placeRoadAddress : String,
        @RequestParam("x") x : String,
        @RequestParam("y") y : String,
    ): ResponseEntity<NewUserResponse> {
        try {
            val user = userService.getMyUser(email)!!

            val imgUrls = mutableListOf<String>()
            val savePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER

            if (!File(savePath).exists()) {
                try {
                    File(savePath).mkdir()
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            for (item in file) {
                val originFileName = item.originalFilename ?: "Empty"
                val filePath = "$savePath/$originFileName"
                item.transferTo(File(filePath))

                imgUrls.add("$BASE_IMAGE_URL${item.originalFilename}")
            }

            val newPostItem = NewPostModel(
                postId = null,
                email = email,
                nickName = user.nickName,
                content = content,
                cityName = placeAddress.split(" ")[0],
                subCityName = placeAddress.split(" ")[1],
                placeName = placeName,
                placeAddress = placeAddress,
                placeRoadAddress = placeRoadAddress,
                x = x,
                y = y,
                profileImg = user.profileImg,
                createdDate = makeDateTimeFormat(),
                imageUrl = imgUrls,
                isBookmarked = false
            )

            val addedPost = user.posts + newPostItem

            val updatedUser = userService.createUser(
                user.copy(
                    posts = addedPost,
                    postCount = addedPost.size
                )
            )

            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
                        result = true,
                        data = updatedUser
                    )
                )
        } catch (e: Exception) {
            e.printStackTrace()

            return ResponseEntity
                .ok()
                .body(
                    NewUserResponse(
                        result = false,
                        errorMsg = "업로드가 실패하였습니다."
                    )
                )
        }
    }

    private fun makeDateTimeFormat(): String {
        val formatter = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formatter)
        val today = Calendar.getInstance().time

        return dateFormat.format(today)
    }


    /***************************************************
     *
     *  [GET] Get Posts By "PlaceName"
     *
     ***************************************************/
    @GetMapping("post")
    private fun getPostsByPlaceName(
        @RequestParam("myEmail"  ) myEmail  : String,
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<PlaceResponse> {
        val posts = postService.findAllByPlaceName(placeName)

        val user = userService.getMyUser(myEmail)!!
        val bookmarkList = bookmarkService.findByUserId(user.userId!!)

        val placeItem = PlaceModel(
            placeName        = posts[0].placeName,
            placeAddress     = posts[0].placeAddress,
            placeRoadAddress = posts[0].placeRoadAddress,
            cityName         = posts[0].cityName,
            subCityName      = posts[0].subCityName,
            isBookmarked = bookmarkList.find { it.placeName == placeName } != null,
            x     = posts[0].x,
            y     = posts[0].y,
            posts = posts.map { post ->
                if (bookmarkList.find { it.placeName == post.placeName } != null) {
                    post.copy(isBookmarked = true)
                } else {
                    post.copy(isBookmarked = false)
                }
            }
        )

        return ResponseEntity
            .ok()
            .body(PlaceResponse(
                result = true,
                data = placeItem
            ))
    }
}