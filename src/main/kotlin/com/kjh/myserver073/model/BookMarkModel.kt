package com.kjh.myserver073.model

import javax.persistence.*

/**
 * myserver073
 * Class: BookMarkModel
 * Created by mac on 2021/09/14.
 *
 * Description:
 */

@Entity
@Table(name = "bookmarks")
data class BookMarkModel(

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