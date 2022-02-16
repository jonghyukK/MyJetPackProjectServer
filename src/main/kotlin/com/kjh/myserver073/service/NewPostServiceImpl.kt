package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewPostModel
import com.kjh.myserver073.repository.PostRepository
import com.kjh.myserver073.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NewPostServiceImpl constructor(
        @Autowired private val postRepository: PostRepository
): NewPostService {

    override fun findAll(): List<NewPostModel> {
        return postRepository.findAll()
    }

    override fun findAllByPlaceName(placeName: String): List<NewPostModel> {
        return postRepository.findAllByPlaceName(placeName).reversed()
    }

    override fun findByPostId(postId: Int): NewPostModel {
        return postRepository.findByPostId(postId)
    }

    override fun findAllByUserId(userId: Int): List<NewPostModel> {
        return postRepository.findAllByUserIdOrderByPostIdDesc(userId)
    }

    override fun findAllByOrderByCreatedAt(pageable: Pageable): List<NewPostModel> {
        return postRepository.findAllByOrderByCreatedAt(pageable)
    }
}