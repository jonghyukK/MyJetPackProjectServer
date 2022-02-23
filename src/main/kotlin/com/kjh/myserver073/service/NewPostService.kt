package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.Post
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface NewPostService {

    fun findAll(): List<Post>

    fun findAllRecentPosts(pageable: Pageable): List<Post>

//    fun findAllByPlaceName(placeName: String): List<Post>
//
//    fun findByPostId(postId: Int): Post
//
//    fun findAllByUserId(userId: Int): List<Post>
//
//    fun findAllByOrderByCreatedAt(pageable: Pageable): List<Post>
}