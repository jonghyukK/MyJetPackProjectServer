package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.NewBookMarkModel
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
interface NewBookMarkService {

    fun findById(id: Int): Optional<NewBookMarkModel>

    @Transactional
    fun findByUserId(userId: Int): List<NewBookMarkModel>

    @Transactional
    fun findAll(): List<NewBookMarkModel>

    @Transactional
    fun deleteByBookmarkId(bookmarkId: Int)
}