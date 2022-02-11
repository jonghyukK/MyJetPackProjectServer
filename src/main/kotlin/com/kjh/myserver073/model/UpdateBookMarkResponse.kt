package com.kjh.myserver073.model

import com.kjh.myserver073.model.entity.NewBookMarkModel

data class UpdateBookMarkResponse(
    val result: Boolean,
    val bookmarks: List<NewBookMarkModel> = listOf(),
    val errorMsg: String? = null
)
