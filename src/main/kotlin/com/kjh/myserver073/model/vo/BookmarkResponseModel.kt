package com.kjh.myserver073.model.vo

data class BookmarkResponseModel(
    val cityName        : String,
    val subCityName     : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x               : String,
    val y               : String,
    val placeImg        : String,
    val isBookmarked    : Boolean = true
)