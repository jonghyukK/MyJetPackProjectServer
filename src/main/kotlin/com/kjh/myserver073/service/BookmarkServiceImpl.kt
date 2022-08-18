package com.kjh.myserver073.service

import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.entity.Bookmark
import com.kjh.myserver073.model.model.BookmarkModel
import com.kjh.myserver073.model.model.toModel
import com.kjh.myserver073.repository.BookmarkRepository
import com.kjh.myserver073.repository.PlaceRepository
import com.kjh.myserver073.repository.PostRepository
import com.kjh.myserver073.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BookmarkServiceImpl constructor(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val postRepository: PostRepository,
    @Autowired private val bookmarkRepository: BookmarkRepository,
    @Autowired private val placeRepository: PlaceRepository
): BookmarkService {

    @Transactional
    override fun updateBookmarks(
        email    : String,
        placeName: String
    ): List<BookmarkModel> {
        val user = userRepository.findUserByEmail(email)!!

        var myBookmarks = bookmarkRepository.findAllByUserId(user.userId!!)
        val existBookmark = myBookmarks.find { it.placeName == placeName }

        myBookmarks = if (existBookmark == null) {
            val place = placeRepository.findByPlaceName(placeName)

            val insertedBookmark = bookmarkRepository.save(Bookmark(
                bookmarkId = null,
                userId     = user.userId,
                placeName  = placeName,
                place      = place!!
            ))

            myBookmarks + insertedBookmark
        } else {
            bookmarkRepository.deleteByBookmarkId(existBookmark.bookmarkId!!)
            myBookmarks.filter { it.bookmarkId != existBookmark.bookmarkId }
        }

        val newUser = userRepository.save(
            user.copy(bookmarks = myBookmarks)
        )

        return newUser.bookmarks.map { it.toModel() }
    }

}