package com.kjh.myserver073.service

import com.kjh.myserver073.model.PlaceVo
import com.kjh.myserver073.model.entity.Place
import com.kjh.myserver073.model.vo.RankingVo
import org.springframework.stereotype.Service

@Service
interface PlaceService {

  fun findAll(): List<Place>

  fun findByPlaceName(placeName: String): PlaceVo?

  fun findAllByUploadCountDesc(): List<RankingVo>
}