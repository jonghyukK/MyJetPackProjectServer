package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.model.vo.UserAndPostsAndBookmarks
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface PostService {

    fun findAll(): List<Post>

    fun findAllRecentPosts(pageable: Pageable): List<Post>

    fun findByPlaceSubCityName(subCityName: String): List<Post>

    fun deletePostByPostId(postId: Int, email: String): UserAndPostsAndBookmarks
}