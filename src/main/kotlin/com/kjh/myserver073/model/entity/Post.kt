package com.kjh.myserver073.model.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener::class)
data class Post(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "post_id")
        val postId: Int? = null,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User,

        @ManyToOne
        @JoinColumn(name = "place_id")
        val place: Place,

        @Column
        val content: String? = null,

        @Convert(converter = StringArrayConverter::class)
        val imageUrl: List<String> = listOf(),

        @Column
        val createdDate: String,

        @CreatedDate
        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now()
) {
        override fun toString(): String {
                return super.toString()
        }
}