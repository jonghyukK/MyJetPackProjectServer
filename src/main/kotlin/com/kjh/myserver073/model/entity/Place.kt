package com.kjh.myserver073.model.entity

import javax.persistence.*

@Entity
@Table(name = "place")
data class Place(

    @Id
    @Column(name = "place_id")
    val placeId: String,

    @Column
    val cityName: String,

    @Column
    val subCityName: String,

    @Column
    val placeName: String,

    @Column
    val placeAddress: String,

    @Column
    val placeRoadAddress: String,

    @Column
    val x : String,

    @Column
    val y: String,

    @Column
    val uploadCount: Int = 0,

    @Column
    val placeImg: String
)