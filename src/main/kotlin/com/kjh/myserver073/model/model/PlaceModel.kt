package com.kjh.myserver073.model.model

import com.kjh.myserver073.model.entity.Place

data class PlaceModel(
    val placeId     : String,
    val cityName    : String,
    val subCityName : String,
    val placeName   : String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x       : String,
    val y       : String,
    val placeImg: String,
    val posts   : List<PostModel> = listOf()
)

fun Place.toModel() =
    PlaceModel(
        placeId      = placeId,
        cityName     = cityName,
        subCityName  = subCityName,
        placeName    = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        x       = x,
        y       = y,
        placeImg= placeImg,
        posts   = posts.map { it.toModel() }
)