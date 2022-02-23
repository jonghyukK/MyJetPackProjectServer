package com.kjh.myserver073.controller

import com.kjh.myserver073.mapper.Mappers
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

            val posts = postService.findAllRecentPosts(pageRequest)

//            val posts = postService.findAllByOrderByCreatedAt(pageRequest)
//            val user = userService.getMyUser(myEmail)

//            val convertPosts = posts.map { post ->
//                post.copy(
////                    isBookmarked = user.bookMarks.find { it.placeName == post.placeName } != null
//                )
//            }

//            val posts = postService.findAll()
//            val str = placeService.findAll().map {
//                it.copy(posts = posts)
//            }

            return ResponseEntity
                .ok()
                .body(
                    PostResponse(
                        result = true,
                        data = Mappers.postListToPostVoList(posts)
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