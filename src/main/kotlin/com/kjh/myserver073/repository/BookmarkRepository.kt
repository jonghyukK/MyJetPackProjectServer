package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.Bookmark
import org.springframework.data.repository.CrudRepository
import java.awt.print.Book

interface BookmarkRepository: CrudRepository<Bookmark, Int> {

    fun findByUserIdAndPostId(userId: Int, postId: Int): Bookmark?

    fun findAllByUserIdAndPostId(userNo: Int, postNo: Int): List<Bookmark>

    fun findAllByUserId(userId: Int): List<Bookmark>

    fun deleteByBookmarkId(bookmarkId: Int)
}