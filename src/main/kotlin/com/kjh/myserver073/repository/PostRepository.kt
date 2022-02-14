package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.NewPostModel
import org.springframework.data.repository.CrudRepository

/**
 * myserver073
 * Class: PostRepository
 * Created by mac on 2021/08/31.
 *
 * Description:
 */
interface PostRepository: CrudRepository<NewPostModel, Int> {

    override fun findAll(): List<NewPostModel>

    fun findAllByPlaceName(placeName: String): List<NewPostModel>

    fun findByPostId(postId: Int): NewPostModel

    fun findAllByUserId(userId: Int): List<NewPostModel>
}