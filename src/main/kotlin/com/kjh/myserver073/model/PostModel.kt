package com.kjh.myserver073.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

/**
 * myserver073
 * Class: FileModel
 * Created by mac on 2021/08/10.
 *
 * Description:
 */
@Entity
@Table(name = "posts")
data class PostModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    val postId: Int?,

    @Column
    val cityCategory: String,

    @ElementCollection(fetch = FetchType.EAGER)
    val imageUrl: List<String>? = listOf(),

    @Column
    val email: String,

    @Column
    val content: String,

    @Column
    val address_name: String,

    @Column
    val category_group_code: String,

    @Column
    val category_group_name: String,

    @Column
    val category_name: String,

    @Column
    val phone: String,

    @Column
    val place_name: String,

    @Column
    val place_url: String,

    @Column
    val road_address_name: String,

    @Column
    val x: String,

    @Column
    val y: String,
)
