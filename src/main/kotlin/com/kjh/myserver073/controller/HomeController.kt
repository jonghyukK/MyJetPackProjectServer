package com.kjh.myserver073.controller

import com.kjh.myserver073.model.BannersResponse
import com.kjh.myserver073.model.vo.BannerVo
import com.kjh.myserver073.service.PostService
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

    @Autowired
    private lateinit var postService: PostService

    /***************************************************
     *
     *  [GET] Get Banners.
     *
     ***************************************************/
    @GetMapping("banners")
    private fun getBanners()
    : ResponseEntity<Any> {
        val banner1 = postService.findByPlaceSubCityName("삼척시")[0].run {
            BannerVo(
                bannerId = 1,
                bannerImg = imageUrl[0],
                bannerTitle = "척하면 떠오르는 삼척 여행",
                bannerTopic = "삼척시"
            )
        }

        val banner2 = postService.findByPlaceSubCityName("단양군")[0].run {
            BannerVo(
                bannerId = 2,
                bannerImg = imageUrl[0],
                bannerTitle = "패러글라이딩의 성지, 단양 여행",
                bannerTopic = "단양군"
            )
        }

        val banner3 = postService.findByPlaceSubCityName("문경시")[0].run {
            BannerVo(
                bannerId = 3,
                bannerImg = imageUrl[0],
                bannerTitle = "문경세재의 고향, 문경 여행",
                bannerTopic = "문경시"
            )
        }


        return ResponseEntity
            .ok()
            .body(
                BannersResponse(
                    result = true,
                    data = listOf(banner1, banner2, banner3)
                )
            )
    }
}