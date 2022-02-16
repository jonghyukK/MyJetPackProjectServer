package com.kjh.myserver073.model

import com.kjh.myserver073.model.entity.NewPostModel

data class PostResponse(
    val result: Boolean,
    val data : List<NewPostModel> = listOf(),
    val errorMsg: String? = null
)