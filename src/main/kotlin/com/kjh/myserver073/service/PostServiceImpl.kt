package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PostServiceImpl constructor(
    @Autowired private val postRepository: PostRepository
): PostService {

    override fun findAll(): List<Post> {
        return postRepository.findAll()
    }

    override fun findAllRecentPosts(pageable: Pageable): List<Post> {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
    }

    override fun findByPlaceSubCityName(subCityName: String): List<Post> {
        return postRepository.findAllByPlaceSubCityNameOrderByCreatedAtDesc(subCityName)
    }

    @Transactional
    override fun deletePostByPostId(postId: Int) {
        postRepository.deleteByPostId(postId)
    }
}