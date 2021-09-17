package com.kjh.myserver073.repository

import com.kjh.myserver073.model.BookMarkModel
import org.springframework.data.repository.CrudRepository
import java.util.*
import javax.transaction.Transactional

/**
 * myserver073
 * Class: BookMarkRepository
 * Created by mac on 2021/09/14.
 *
 * Description:
 */
interface BookMarkRepository: CrudRepository<BookMarkModel, Int> {

    override fun findById(id: Int): Optional<BookMarkModel>

    @Transactional
    fun findByUserId(userId: Int): List<BookMarkModel>

    @Transactional
    override fun findAll(): List<BookMarkModel>

    @Transactional
    fun deleteByBookmarkId(bookmarkId: Int)
}