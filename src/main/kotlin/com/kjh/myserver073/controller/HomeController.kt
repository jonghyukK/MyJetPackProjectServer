package com.kjh.myserver073.controller

import com.kjh.myserver073.model.common.ApiResponse
import com.kjh.myserver073.model.common.toResponseEntity
import com.kjh.myserver073.model.model.BannerModel
import com.kjh.myserver073.service.PlaceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping
class HomeController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var placeService: PlaceService

    /***************************************************
     *
     *  [GET] Get Banners.
     *
     ***************************************************/
    @GetMapping("banners")
    private fun getBanners(): ResponseEntity<ApiResponse> =
        try {
            val banner1 = placeService.findAllBySubCityName("삼척시").run {
                BannerModel(
                    bannerId = 1,
                    bannerImg = this[0].placeImg,
                    bannerTitle = "척하면 떠오르는 삼척 여행",
                    bannerTopic = "삼척시"
                )
            }

            val banner2 = placeService.findAllBySubCityName("단양군").run {
                BannerModel(
                    bannerId = 2,
                    bannerImg = this[0].placeImg,
                    bannerTitle = "패러글라이딩의 성지, 단양 여행",
                    bannerTopic = "단양군"
                )
            }

            val banner3 = placeService.findAllBySubCityName("문경시").run {
                BannerModel(
                    bannerId = 3,
                    bannerImg = this[0].placeImg,
                    bannerTitle = "문경세재의 고향, 문경 여행",
                    bannerTopic = "문경시"
                )
            }

            ApiResponse(
                result = true,
                data = listOf(banner1, banner2, banner3)
            ).toResponseEntity()
        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "Failed Home Banners Data"
            ).toResponseEntity()
        }
}