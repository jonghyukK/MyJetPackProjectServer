package com.kjh.myserver073.controller

import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.PostResponse
import com.kjh.myserver073.service.PostService
import com.kjh.myserver073.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping
class PostController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var userService: UserService

    /***************************************************
     *
     *  [GET] Get Posts by created
     *
     ***************************************************/
    @GetMapping("post/recent")
    private fun getPostsByCreated(
        @RequestParam("page") page: Int = 0,
        @RequestParam("size") size: Int = 3
    ): ResponseEntity<PostResponse> {
        try {
            val pageRequest = PageRequest.of(page, size)

            val posts = postService.findAllRecentPosts(pageRequest)

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