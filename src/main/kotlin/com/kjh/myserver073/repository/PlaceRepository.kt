package com.kjh.myserver073.repository

import com.kjh.myserver073.model.entity.Place
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository

interface PlaceRepository: CrudRepository<Place, Int> {
    override fun findAll(): List<Place>

    @EntityGraph(attributePaths = ["posts"])
    fun findByPlaceName(placeName: String): Place?

    @EntityGraph(attributePaths = ["posts"])
    fun findAllByOrderByUploadCountDesc(): List<Place>

    @EntityGraph(attributePaths = ["posts"])
    fun findAllBySubCityName(subCityName: String): List<Place>

    @EntityGraph(attributePaths = ["posts"])
    fun findAllByCityName(cityName: String, pageable: Pageable): List<Place>

    @EntityGraph(attributePaths = ["posts"])
    fun findBySubCityName(subCityName: String): Place
}