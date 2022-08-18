package com.kjh.myserver073.service

import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.model.PlaceModel
import com.kjh.myserver073.model.model.PlaceWithRankingModel
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
interface PlaceService {

  fun findAll(): List<Place>

  fun findByPlaceName(placeName: String): PlaceModel?

  fun findByPlaceNameWithAroundPaging(placeName: String, pageable: Pageable): List<PlaceModel>

  fun findAllByUploadCountDesc(): List<PlaceWithRankingModel>

  fun findAllBySubCityName(subCityName: String): List<PlaceModel>

  fun findBySubCityName(subCityName: String): PlaceModel
}