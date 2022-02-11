package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewBookMarkModel
import com.kjh.myserver073.repository.BookMarkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class NewBookMarkServiceImpl constructor(
    @Autowired private val bookMarkRepository: BookMarkRepository
): NewBookMarkService {
    override fun findById(id: Int): Optional<NewBookMarkModel> {
        return bookMarkRepository.findById(id)
    }

    override fun findByUserId(userId: Int): List<NewBookMarkModel> {
        return bookMarkRepository.findByUserId(userId)
    }

    override fun findAll(): List<NewBookMarkModel> {
        return bookMarkRepository.findAll()
    }

    override fun deleteByBookmarkId(bookmarkId: Int) {
        bookMarkRepository.deleteByBookmarkId(bookmarkId)
    }
}