package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewPostModel
import com.kjh.myserver073.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
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
}