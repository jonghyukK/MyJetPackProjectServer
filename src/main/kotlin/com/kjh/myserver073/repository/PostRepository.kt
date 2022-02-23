package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.Post
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

/**
 * myserver073
 * Class: PostRepository
 * Created by mac on 2021/08/31.
 *
 * Description:
 */
interface PostRepository: CrudRepository<Post, Int> {

    fun findAllByUserUserIdOrderByCreatedAtDesc(userid: Int): List<Post>

    fun findAllByUserUserId(userId: Int): List<Post>

    fun findAllByPlacePlaceId(placeId: String): List<Post>

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): List<Post>

    override fun findAll(): List<Post>
}