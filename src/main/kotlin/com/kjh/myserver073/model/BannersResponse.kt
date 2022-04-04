package com.kjh.myserver073.model

import com.kjh.myserver073.model.vo.BannerVo

data class BannersResponse(
    val result  : Boolean,
    val data    : List<BannerVo>,
    val errorMsg: String? = null
)