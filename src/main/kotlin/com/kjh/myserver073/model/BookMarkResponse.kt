package com.kjh.myserver073.model

import com.kjh.myserver073.model.vo.PostVo

data class BookMarkResponse(
    val result: Boolean,
    val data  : List<PostVo> = listOf(),
    val errorMsg: String? = null
)