package com.kjh.myserver073.model.vo

data class BookmarkVo(
    val postId: Int,
    val email: String,
    val nickName: String,
    val profileImg: String? = null,
    val content: String? = null,
    val cityName: String,
    val subCityName: String,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x : String,
    val y: String,
    val createdDate: String,
    val imageUrl: List<String> = listOf(),
    val isBookmarked: Boolean = true
)