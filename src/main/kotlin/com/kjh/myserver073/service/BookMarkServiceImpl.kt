package com.kjh.myserver073.service

import com.kjh.myserver073.model.BookMarkModel
import com.kjh.myserver073.repository.BookMarkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * myserver073
 * Class: BookMarkServiceImpl
 * Created by mac on 2021/09/14.
 *
 * Description:
 */
@Service
class BookMarkServiceImpl constructor(
    @Autowired private val bookMarkRepository: BookMarkRepository
): BookMarkService {
    override fun findById(id: Int): Optional<BookMarkModel> {
        return bookMarkRepository.findById(id)
    }

    override fun findByUserId(userId: Int): List<BookMarkModel> {
        return bookMarkRepository.findByUserId(userId)
    }

    override fun findAll(): List<BookMarkModel> {
        return bookMarkRepository.findAll()
    }

    override fun deleteByBookmarkId(bookmarkId: Int) =
        bookMarkRepository.deleteByBookmarkId(bookmarkId)

}