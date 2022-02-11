package com.kjh.myserver073.model.entity

import javax.persistence.*

@Entity
@Table(name ="bookmarks")
data class NewBookMarkModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bookmark_id")
    val bookmarkId: Int? = 0,

    @Column
    val userId: Int,

    @Column
    val postId: Int,

    @Column
    val placeName: String,
)