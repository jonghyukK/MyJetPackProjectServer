package com.kjh.myserver073.model

import com.kjh.myserver073.model.entity.NewPostModel

data class PlaceModel(
   val placeName: String,
   val placeAddress: String,
   val placeRoadAddress: String,
   val cityName: String,
   val subCityName: String,
   val x: String,
   val y: String,
   val isBookmarked: Boolean = false,
   val posts : List<NewPostModel>
)

data class PlaceResponse(
    val result: Boolean,
    val data: PlaceModel,
    val errorMsg: String? = null
)