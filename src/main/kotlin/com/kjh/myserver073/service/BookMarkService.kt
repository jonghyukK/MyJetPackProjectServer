package com.kjh.myserver073.service

import com.kjh.myserver073.model.BookMarkModel
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 * myserver073
 * Class: BookMarkService
 * Created by mac on 2021/09/14.
 *
 * Description:
 */
@Service
interface BookMarkService {

    fun findById(id: Int): Optional<BookMarkModel>

    @Transactional
    fun findByUserId(userId: Int): List<BookMarkModel>

    @Transactional
    fun findAll(): List<BookMarkModel>

    @Transactional
    fun deleteByBookmarkId(bookmarkId: Int)
}