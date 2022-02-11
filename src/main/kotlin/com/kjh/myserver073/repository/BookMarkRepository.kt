package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.NewBookMarkModel
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
interface BookMarkRepository: CrudRepository<NewBookMarkModel, Int> {

    override fun findById(id: Int): Optional<NewBookMarkModel>

    @Transactional
    fun findByUserId(userId: Int): List<NewBookMarkModel>

    @Transactional
    override fun findAll(): List<NewBookMarkModel>

    @Transactional
    fun deleteByBookmarkId(bookmarkId: Int)
}