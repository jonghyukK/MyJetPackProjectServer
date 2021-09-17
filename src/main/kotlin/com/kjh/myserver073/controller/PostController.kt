package com.kjh.myserver073.controller

import com.kjh.myserver073.model.PostModel
import com.kjh.myserver073.model.UserVo
import com.kjh.myserver073.service.PostService
import com.kjh.myserver073.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * myserver073
 * Class: PostController
 * Created by mac on 2021/09/01.
 *
 * Description:
 */

@RestController
@RequestMapping
class PostController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var userService: UserService

    @DeleteMapping("post")
    private fun deletePost(
        @RequestParam(value = "postId") postId: Int,
        @RequestParam(value = "email") email: String,
    ): ResponseEntity<Any> {
        postService.deleteByPostId(postId)

        val updateUser =
            with(userService.getUserByEmail(email)!!) {
                copy(postCount = posts.size)
            }.run {
                userService.createUser(this)
            }

        val mapPostsByCityCategory = if (updateUser.posts.isNotEmpty())
            mapOf("전체" to updateUser.posts) + updateUser.posts.groupBy({ it.cityCategory }, { it })
        else
            updateUser.posts.groupBy({ it.cityCategory }, { it })

        return ResponseEntity
            .ok()
            .body(UserVo(
                updateUser.userId,
                updateUser.email,
                updateUser.pw,
                updateUser.posts.size,
                updateUser.followingCount,
                updateUser.followCount,
                updateUser.profileImg,
                mutableListOf(),
                mapPostsByCityCategory))
    }

    @GetMapping("post")
    private fun getPosts(
        @RequestParam(value = "city") cityName: String? = null,
        @RequestParam(value = "placeName") placeName: String? = null
    ): ResponseEntity<Any> {
        if (cityName != null) {
            return ResponseEntity
                .ok()
                .body(postService.findAllByCityCategory(cityName).reversed())
        }

        if (placeName != null) {
            return ResponseEntity
                .ok()
                .body(postService.findAllByPlaceName(placeName))
        }

        val posts = postService.findAll()

        return if (posts.size in 1..9) {
            ResponseEntity
                .ok()
                .body(posts.reversed())
        } else {
            ResponseEntity
                .ok()
                .body(posts.reversed().slice(0 .. 9))
        }
    }
}