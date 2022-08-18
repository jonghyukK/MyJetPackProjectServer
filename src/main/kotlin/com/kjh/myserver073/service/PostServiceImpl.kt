package com.kjh.myserver073.service

import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.model.entity.User
import com.kjh.myserver073.model.vo.UserAndPostsAndBookmarks
import com.kjh.myserver073.repository.BookmarkRepository
import com.kjh.myserver073.repository.PlaceRepository
import com.kjh.myserver073.repository.PostRepository
import com.kjh.myserver073.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PostServiceImpl constructor(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val postRepository: PostRepository,
    @Autowired private val placeRepository: PlaceRepository,
    @Autowired private val bookmarkRepository: BookmarkRepository
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
    override fun deletePostByPostId(postId: Int, email: String): UserAndPostsAndBookmarks {
        val user = userRepository.findUserByEmail(email)!!

        postRepository.deleteByPostId(postId)

        val placeName = user.posts.find { it.postId == postId }!!.place.placeName
        val existOtherPosts = placeRepository.findByPlaceName(placeName)!!.posts.any { it.postId != postId }

        val newUser: User

        if (!existOtherPosts) {
            placeRepository.deleteByPlaceId(placeName)

            val bookmarkId = user.bookmarks.find { it.placeName == placeName }!!.bookmarkId!!
            bookmarkRepository.deleteByBookmarkId(bookmarkId)

            newUser = userRepository.save(user.copy(
                postCount = user.posts.size - 1,
                posts = user.posts.filter { it.postId != postId },
                bookmarks = user.bookmarks.filter { it.bookmarkId != bookmarkId }
            ))
        } else {
            newUser = userRepository.save(user.copy(
                postCount = user.posts.size - 1,
                posts = user.posts.filter { it.postId != postId }
            ))
        }

        return Mappers.makeUserAndPostsAndBookmarks(newUser)
    }
}