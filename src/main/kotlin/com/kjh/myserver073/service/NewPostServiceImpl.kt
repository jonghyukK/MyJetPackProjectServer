package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NewPostServiceImpl constructor(
        @Autowired private val postRepository: PostRepository
): NewPostService {

    override fun findAll(): List<Post> {
        return postRepository.findAll()
    }

    override fun findAllRecentPosts(pageable: Pageable): List<Post> {
        val posts = postRepository.findAllByOrderByCreatedAtDesc(pageable)

        return posts
    }

    override fun findByPlaceSubCityName(subCityName: String): List<Post> {
        return postRepository.findAllByPlaceSubCityNameOrderByCreatedAtDesc(subCityName)
    }

//
//    override fun findAllByPlaceName(placeName: String): List<Post> {
//        return postRepository.findAllByPlaceName(placeName).reversed()
//    }
//
//    override fun findByPostId(postId: Int): Post {
//        return postRepository.findById(postId).get()
//    }
//
//    override fun findAllByUserId(userId: Int): List<Post> {
//        return postRepository.findAllByUserIdOrderByUserIdDesc(userId)
//    }
//
//    override fun findAllByOrderByCreatedAt(pageable: Pageable): List<Post> {
//        return postRepository.findAllByOrderByCreatedAt(pageable)
//    }
}