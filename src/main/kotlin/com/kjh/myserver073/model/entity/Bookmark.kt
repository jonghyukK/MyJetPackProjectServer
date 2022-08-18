package com.kjh.myserver073.model.entity

import javax.persistence.*

@Entity
data class Bookmark(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookmark_id")
    val bookmarkId: Int? = 0,

    @Column(name = "user_id")
    val userId: Int,

    @Column
    val placeName: String,

    @ManyToOne
    @JoinColumn(name = "place_id")
    val place: Place,
)