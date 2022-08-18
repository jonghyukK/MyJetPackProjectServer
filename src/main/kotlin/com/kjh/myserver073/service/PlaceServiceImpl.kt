package com.kjh.myserver073.service

import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.model.PlaceModel
import com.kjh.myserver073.model.model.PlaceWithRankingModel
import com.kjh.myserver073.model.model.toModel
import com.kjh.myserver073.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class PlaceServiceImpl constructor(
    @Autowired private val placeRepository: PlaceRepository
): PlaceService {

    override fun findAll(): List<Place> {
        return placeRepository.findAll()
    }

    override fun findByPlaceName(placeName: String): PlaceModel? {
        val place = placeRepository.findByPlaceName(placeName)!!

        return place.toModel()
    }

    override fun findByPlaceNameWithAroundPaging(
        placeName: String,
        pageable : Pageable
    ): List<PlaceModel> =
        placeRepository.findByPlaceName(placeName)?.let { place ->
            placeRepository.findAllByCityName(place.cityName, pageable).map { it.toModel() }
        } ?: emptyList()


    override fun findAllByUploadCountDesc(): List<PlaceWithRankingModel> =
        Mappers.makePlaceWithRanking(placeRepository.findAllByOrderByUploadCountDesc())

    override fun findAllBySubCityName(subCityName: String): List<PlaceModel> =
        placeRepository.findAllBySubCityName(subCityName).map { it.toModel() }

    override fun findBySubCityName(subCityName: String): PlaceModel {
        val place = placeRepository.findBySubCityName(subCityName).toModel()

        return place
    }
}