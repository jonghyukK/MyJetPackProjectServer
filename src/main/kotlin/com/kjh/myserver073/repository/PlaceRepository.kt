package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.Place
import org.springframework.data.repository.CrudRepository

interface PlaceRepository: CrudRepository<Place, Int> {
    override fun findAll(): List<Place>

    fun findByPlaceName(placeName: String): Place?

    fun findAllByOrderByUploadCountDesc(): List<Place>

    fun findAllBySubCityName(subCityName: String): List<Place>
}