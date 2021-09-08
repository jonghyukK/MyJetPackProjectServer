package com.kjh.myserver073.service

import com.kjh.myserver073.model.PostModel
import com.kjh.myserver073.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * myserver073
 * Class: PostServiceImpl
 * Created by mac on 2021/09/01.
 *
 * Description:
 */
@Service
class PostServiceImpl constructor(
    @Autowired private val postRepository: PostRepository
): PostService {

    @Transactional
    override fun deleteByPostId(postId: Int): Int {
        postRepository.deleteByPostId(postId)
        return postId
    }

    @Transactional
    override fun findAll(): List<PostModel> {
        return postRepository.findAll()
    }

    override fun findAllByCityCategory(cityCategory: String): List<PostModel> {
        return postRepository.findByCityCategory(cityCategory)
    }
}