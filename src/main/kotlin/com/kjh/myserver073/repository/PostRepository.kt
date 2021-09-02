package com.kjh.myserver073.repository

import com.kjh.myserver073.model.PostModel
import org.springframework.data.repository.CrudRepository

/**
 * myserver073
 * Class: PostRepository
 * Created by mac on 2021/08/31.
 *
 * Description:
 */
interface PostRepository: CrudRepository<PostModel, Int> {

    fun deleteByPostId(postId: Int): Int

}