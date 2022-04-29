package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.Bookmark
import org.springframework.data.repository.CrudRepository

interface BookmarkRepository: CrudRepository<Bookmark, Int> {

    fun findByUserIdAndPlaceName(userId: Int, placeName: String): Bookmark?

    fun findAllByUserId(userId: Int): List<Bookmark>

    fun deleteByBookmarkId(bookmarkId: Int)
}