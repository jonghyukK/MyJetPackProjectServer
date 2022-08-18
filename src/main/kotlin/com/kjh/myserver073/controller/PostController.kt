package com.kjh.myserver073.controller

import com.kjh.myserver073.model.common.ApiResponse
import com.kjh.myserver073.model.common.toResponseEntity
import com.kjh.myserver073.model.model.toModel
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
    ): ResponseEntity<ApiResponse> {
        try {
            val pageRequest = PageRequest.of(page, size)

            return ResponseEntity
                .ok()
                .body(
                    ApiResponse(
                        result = true,
                        data   = postService.findAllRecentPosts(pageRequest).map { it.toModel() }
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    ApiResponse(
                        result = false,
                        errorMsg = "최근 여행지 불러오기를 실패하였습니다."
                    )
                )
        }
    }

    /***************************************************
     *
     *  [DELETE] Delete Posts
     *
     ***************************************************/
    @DeleteMapping("post/delete")
    private fun deletePostByPostId(
        @RequestParam("postId") postId: Int,
        @RequestParam("email" ) email : String
    ): ResponseEntity<ApiResponse> =
        try {
            ApiResponse(
                result = true,
                data = postService.deletePostByPostId(postId, email)
            ).toResponseEntity()

        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "Failed Delete Post."
            ).toResponseEntity()
        }
}