package com.kjh.myserver073.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener::class)
data class NewPostModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "post_id")
        val postId: Int? = null,

        @Column(name = "user_id")
        val userId: Int? = null,

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

        val isBookmarked: Boolean = false,

        @CreatedDate
        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),
)