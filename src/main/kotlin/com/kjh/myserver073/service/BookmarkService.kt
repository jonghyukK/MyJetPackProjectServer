package com.kjh.myserver073.service

import com.kjh.myserver073.model.model.BookmarkModel
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
interface BookmarkService {

    @Transactional
    fun updateBookmarks(
        email    : String,
        placeName: String
    ): List<BookmarkModel>
}