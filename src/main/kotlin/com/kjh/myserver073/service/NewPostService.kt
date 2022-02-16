package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewPostModel
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface NewPostService {

    fun findAll(): List<NewPostModel>

    fun findAllByPlaceName(placeName: String): List<NewPostModel>

    fun findByPostId(postId: Int): NewPostModel

    fun findAllByUserId(userId: Int): List<NewPostModel>

    fun findAllByOrderByCreatedAt(pageable: Pageable): List<NewPostModel>
}