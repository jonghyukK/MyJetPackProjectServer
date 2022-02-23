package com.kjh.myserver073.model

import com.kjh.myserver073.model.vo.PostVo

data class PlaceVo(
    val placeId: String,
    val cityName: String,
    val subCityName: String,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x: String,
    val y: String,
    val placeImg: String,
    val posts: List<PostVo> = listOf()
)

data class PlaceResponse(
    val result: Boolean,
    val data: PlaceVo? = null,
    val errorMsg: String? = null
)