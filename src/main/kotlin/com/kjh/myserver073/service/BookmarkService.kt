package com.kjh.myserver073.service

import com.kjh.myserver073.model.vo.BookmarkVo
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
interface BookmarkService {

    @Transactional
    fun getBookmarks(email: String): List<BookmarkVo>

    @Transactional
    fun updateBookmarks(email: String,
                     postId: Int,
                     placeName: String): List<BookmarkVo>
}