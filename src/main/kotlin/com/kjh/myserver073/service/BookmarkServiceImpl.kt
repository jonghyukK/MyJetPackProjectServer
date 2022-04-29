package com.kjh.myserver073.service

import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.entity.Bookmark
import com.kjh.myserver073.model.vo.BookmarkVo
import com.kjh.myserver073.repository.BookmarkRepository
import com.kjh.myserver073.repository.PostRepository
import com.kjh.myserver073.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BookmarkServiceImpl constructor(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val postRepository: PostRepository,
    @Autowired private val bookmarkRepository: BookmarkRepository
): BookmarkService {

    override fun getBookmarks(email: String): List<BookmarkVo> {
        val user = userRepository.findUserByEmail(email)!!

        val bookmarks = bookmarkRepository.findAllByUserId(user.userId!!).map { bookmark ->
            postRepository.findById(bookmark.postId).get()
        }

        return Mappers.postListToBookmarkVoList(bookmarks)
    }

    @Transactional
    override fun updateBookmarks(
        email    : String,
        postId   : Int,
        placeName: String
    ): List<BookmarkVo> {
        val user = userRepository.findUserByEmail(email)!!

        val existBookmark = bookmarkRepository.findByUserIdAndPlaceName(user.userId!!, placeName)

        if (existBookmark == null) {
            bookmarkRepository.save(Bookmark(
                bookmarkId = null,
                userId     = user.userId,
                postId     = postId,
                placeName  = placeName
            ))
        } else {
            bookmarkRepository.deleteByBookmarkId(existBookmark.bookmarkId!!)
        }

        val bookmarks = bookmarkRepository.findAllByUserId(user.userId).map { bookmark ->
            postRepository.findById(bookmark.postId).get()
        }

        return Mappers.postListToBookmarkVoList(bookmarks)
    }

}