package com.kjh.myserver073.controller

import com.kjh.myserver073.model.common.ApiResponse
import com.kjh.myserver073.model.common.toResponseEntity
import com.kjh.myserver073.service.PlaceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class PlaceController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var placeService: PlaceService

    /***************************************************
     *
     *  [GET] Get Place by Upload Count.
     *
     ***************************************************/
    @GetMapping("place/ranking")
    private fun getPlacesByRanking(): ResponseEntity<ApiResponse> =
        try {
            ApiResponse(
                result = true,
                data = placeService.findAllByUploadCountDesc()
            ).toResponseEntity()
        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "랭킹 정보를 가져오는데 실패하였습니다."
            ).toResponseEntity()
        }


    /***************************************************
     *
     *  [GET] Get Place By "PlaceName"
     *
     ***************************************************/
    @GetMapping("place")
    private fun getPlaceByPlaceName(
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<ApiResponse> =
        try {
            val data = placeService.findByPlaceName(placeName)

            ApiResponse(
                result = true,
                data = data
            ).toResponseEntity()

        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "장소 불러오기를 실패하였습니다."
            ).toResponseEntity()
        }


    /***************************************************
     *
     *  [GET] Get Place By "PlaceName" with Around Places.
     *
     ***************************************************/
    @GetMapping("place/around")
    private fun getPlaceByPlaceNameWithAround(
        @RequestParam("placeName") placeName: String,
        @RequestParam("page") page: Int = 0,
        @RequestParam("size") size: Int = 4
    ): ResponseEntity<ApiResponse> =
        try {
            val pageRequest = PageRequest.of(page, size)
            ApiResponse(
                result = true,
                data = placeService.findByPlaceNameWithAroundPaging(
                    placeName, pageRequest
                )
            ).toResponseEntity()
        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "장소 불러오기를 실패하였습니다."
            ).toResponseEntity()
        }


    /***************************************************
     *
     *  [GET] Get Place List By "SubCityName"
     *
     ***************************************************/
    @GetMapping("place/subCityName")
    private fun getPlacesBySubCityName(
        @RequestParam("subCityName") subCityName: String
    ): ResponseEntity<ApiResponse> =
        try {
            ApiResponse(
                result = true,
                data = placeService.findAllBySubCityName(subCityName)
            ).toResponseEntity()
        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "SubCityName에 따른 장소 불러오기를 실패하였습니다."
            ).toResponseEntity()
        }
}