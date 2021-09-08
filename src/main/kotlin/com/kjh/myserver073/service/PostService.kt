package com.kjh.myserver073.service

import com.kjh.myserver073.model.PostModel
import org.springframework.stereotype.Service

/**
 * myserver073
 * Class: PostService
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
@Service
interface PostService {

    fun deleteByPostId(postId: Int): Int

    fun findAll(): List<PostModel>

    fun findAllByCityCategory(cityCategory: String): List<PostModel>
}