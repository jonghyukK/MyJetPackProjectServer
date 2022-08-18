package com.kjh.myserver073.model.model

import com.kjh.myserver073.model.entity.Bookmark

data class BookmarkModel(
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

fun Bookmark.toModel() =
    BookmarkModel(
        cityName         = place.cityName,
        subCityName      = place.subCityName,
        placeName        = place.placeName,
        placeAddress     = place.placeAddress,
        placeRoadAddress = place.placeRoadAddress,
        x                = place.x,
        y                = place.y,
        placeImg         = place.placeImg,
        isBookmarked     = true
    )