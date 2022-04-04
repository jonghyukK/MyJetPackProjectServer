package com.kjh.myserver073.service

import com.kjh.myserver073.mapper.Mappers
import com.kjh.myserver073.model.PlaceVo
import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.entity.Post
import com.kjh.myserver073.model.vo.RankingVo
import com.kjh.myserver073.repository.PlaceRepository
import com.kjh.myserver073.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PlaceServiceImpl constructor(
    @Autowired private val placeRepository: PlaceRepository,
    @Autowired private val postRepository: PostRepository
): PlaceService {
    override fun findAll(): List<Place> {
        return placeRepository.findAll()
    }

    override fun findByPlaceName(placeName: String): PlaceVo? {
        val place = placeRepository.findByPlaceName(placeName)!!
        val posts = postRepository.findAllByPlacePlaceId(place.placeId)

        return Mappers.placeToPlaceVoWithPosts(place, posts)
    }

    override fun findAllByUploadCountDesc(): List<RankingVo> {
        val places = placeRepository.findAllByOrderByUploadCountDesc()

        return Mappers.placeToRankingVo(places)
    }

    override fun findAllBySubCityName(subCityName: String): List<PlaceVo> {
        val placeList = placeRepository.findAllBySubCityName(subCityName)
        val postListByPlace = placeList.map { place ->
            Mappers.placeToPlaceVoWithPosts(place, postRepository.findAllByPlacePlaceId(place.placeName))
        }

        return postListByPlace

//        val str = postRepository.findAllByPlaceSubCityNameOrderByCreatedAtDesc(subCityName)
//            .groupBy { it.place.placeName }
//
//        return str
    }
}