package com.kjh.myserver073.controller

import com.kjh.myserver073.model.PlaceModel
import com.kjh.myserver073.model.PlaceResponse
import com.kjh.myserver073.model.PostResponse
import com.kjh.myserver073.service.NewPostService
import com.kjh.myserver073.service.NewUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping
class NewPostController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var postService: NewPostService

    @Autowired
    private lateinit var userService: NewUserService

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

        val placeItem = PlaceModel(
            placeName        = posts[0].placeName,
            placeAddress     = posts[0].placeAddress,
            placeRoadAddress = posts[0].placeRoadAddress,
            cityName         = posts[0].cityName,
            subCityName      = posts[0].subCityName,
            x     = posts[0].x,
            y     = posts[0].y,
            posts = posts
        )

        return ResponseEntity
            .ok()
            .body(PlaceResponse(
                result = true,
                data = placeItem
            ))
    }

    /***************************************************
     *
     *  [GET] Get Posts by created
     *
     ***************************************************/
    @GetMapping("post/recent")
    private fun getPostsByCreated(
        @RequestParam("myEmail") myEmail: String,
        @RequestParam("page") page: Int = 0,
        @RequestParam("size") size: Int = 3
    ): ResponseEntity<PostResponse> {
        try {
            val pageRequest = PageRequest.of(page, size)

            val posts = postService.findAllByOrderByCreatedAt(pageRequest)
            val user = userService.getMyUser(myEmail)

            val convertPosts = posts.map { post ->
                post.copy(
                    isBookmarked = user.bookMarks.find { it.placeName == post.placeName } != null
                )
            }

            return ResponseEntity
                .ok()
                .body(
                    PostResponse(
                        result = true,
                        data = convertPosts
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    PostResponse(
                        result = false,
                        errorMsg = "최근 여행지 불러오기를 실패하였습니다."
                    )
                )
        }
    }
}