package com.kjh.myserver073.model.vo

import com.kjh.myserver073.model.PlaceVo

data class RankingVo(
    val rank: Int,
    val place: PlaceVo
)

data class RankingResponse(
    val result: Boolean,
    val data: List<RankingVo>? = null,
    val errorMsg: String? = null
)