package com.kjh.myserver073.model.entity

import javax.persistence.*


@Entity
@Table(name = "posts")
data class NewPostModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "post_id")
        val postId: Int? = null,

        @Column
        val email: String,

        @Column
        val nickName: String,

        @Column
        val content: String? = null,

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

        @Column(name = "profileImg")
        val profileImg: String? = null,

        @Column
        val createdDate: String,

        @ElementCollection(fetch = FetchType.EAGER)
        val imageUrl: List<String>? = listOf(),

        val isBookmarked: Boolean = false
)