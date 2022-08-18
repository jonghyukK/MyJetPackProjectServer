package com.kjh.myserver073.model.model

import com.kjh.myserver073.model.entity.Post

data class PostModel(
    val postId      : Int,
    val email       : String,
    val nickName    : String,
    val profileImg  : String? = null,
    val content     : String? = null,
    val cityName    : String,
    val subCityName : String,
    val placeName   : String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x               : String,
    val y               : String,
    val createdDate     : String,
    val imageUrl        : List<String> = listOf()
)

fun Post.toModel() =
    PostModel(
        postId      = postId!!,
        content     = content,
        createdDate = createdDate,
        imageUrl    = imageUrl,
        profileImg  = user.profileImg,
        email       = user.email,
        nickName    = user.nickName,
        cityName    = place.cityName,
        subCityName = place.subCityName,
        placeName   = place.placeName,
        placeAddress    = place.placeAddress,
        placeRoadAddress= place.placeRoadAddress,
        x               = place.x,
        y               = place.y
    )