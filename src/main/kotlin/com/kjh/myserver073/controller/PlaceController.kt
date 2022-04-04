package com.kjh.myserver073.controller

import com.kjh.myserver073.model.PlaceListResponse
import com.kjh.myserver073.model.PlaceResponse
import com.kjh.myserver073.model.vo.RankingResponse
import com.kjh.myserver073.service.PlaceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
    private fun getPlacesByRanking()
    : ResponseEntity<RankingResponse> {
        try {
            return ResponseEntity
                .ok()
                .body(
                    RankingResponse(
                        result = true,
                        data = placeService.findAllByUploadCountDesc()
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    RankingResponse(
                        result = false,
                        errorMsg = "랭킹 정보를 가져오는데 실패하였습니다."
                    )
                )
        }
    }

    /***************************************************
     *
     *  [GET] Get Place By "PlaceName"
     *
     ***************************************************/
    @GetMapping("place")
    private fun getPlaceByPlaceName(
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<PlaceResponse> {
        try {
            return ResponseEntity
                .ok()
                .body(
                    PlaceResponse(
                        result = true,
                        data = placeService.findByPlaceName(placeName)
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    PlaceResponse(
                        result = false,
                        errorMsg = "장소 불러오기를 실패하였습니다."
                    )
                )
        }
    }

    /***************************************************
     *
     *  [GET] Get Place List By "SubCityName"
     *
     ***************************************************/
    @GetMapping("place/subCityName")
    private fun getPlacesBySubCityName(
        @RequestParam("subCityName") subCityName: String
    ): ResponseEntity<PlaceListResponse> {
        try {
            return ResponseEntity
                .ok()
                .body(
                    PlaceListResponse(
                        result = true,
                        data = placeService.findAllBySubCityName(subCityName)
                    )
                )
        } catch(e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    PlaceListResponse(
                        result = false,
                        errorMsg = "SubCityName에 따른 장소 불러오기를 실패하였습니다."
                    )
                )
        }
    }
}