package com.kjh.myserver073.model.entity

import java.util.*
import java.util.stream.Collectors
import javax.persistence.*


@Entity
@Table(name = "users")
data class NewUserModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name ="user_id")
        val userId: Int? = 0,

        @Column
        val email: String,

        @Column
        val nickName: String,

        @Column
        val introduce: String? = null,

        @Column
        val pw: String,

        @Column
        val followingCount: Int = 0,

        @Column
        val followCount: Int = 0,

        @Column
        val postCount: Int = 0,

        @Convert(converter = StringArrayConverter::class)
        val followList: List<String> = listOf(),

        @Convert(converter = StringArrayConverter::class)
        val followingList: List<String> = listOf(),

        @Column
        val profileImg: String? = null,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "user_id")
        val posts: List<NewPostModel> = listOf(),

        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @OrderBy("created_at")
        val bookMarks: List<NewPostModel> = listOf(),

        val isFollowing: Boolean? = null,
)

@Converter
class StringArrayConverter : AttributeConverter<List<String>, String> {
        companion object {
                const val SPLIT_CHAR = ","
        }

        override fun convertToDatabaseColumn(attribute: List<String>): String {
                return attribute.stream().collect(Collectors.joining(SPLIT_CHAR))
        }

        override fun convertToEntityAttribute(dbData: String?): List<String> {
                return if (dbData != null && dbData != "") {
                        Arrays.stream(dbData.split(SPLIT_CHAR).toTypedArray())
                                .collect(Collectors.toList())
                } else {
                        listOf()
                }
        }
}
