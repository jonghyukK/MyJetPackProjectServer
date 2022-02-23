package com.kjh.myserver073.model

import com.kjh.myserver073.model.vo.PostVo

data class BookMarkResponse(
    val result: Boolean,
    val bookMarks: List<PostVo> = listOf(),
    val errorMsg: String? = null
)