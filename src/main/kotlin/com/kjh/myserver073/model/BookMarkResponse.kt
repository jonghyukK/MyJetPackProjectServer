package com.kjh.myserver073.model

import com.kjh.myserver073.model.entity.NewPostModel

data class BookMarkResponse(
    val result: Boolean,
    val bookMarks: List<NewPostModel> = listOf(),
    val errorMsg: String? = null
)