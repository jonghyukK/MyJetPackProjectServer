package com.kjh.myserver073.model

import javax.persistence.*

/**
 * myserver073
 * Class: UserModel
 * Created by mac on 2021/07/27.
 *
 * Description:
 */

@Entity
@Table(name = "users")
data class UserModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    val userId: Int? = 0,

    @Column
    val email: String,

    @Column
    val pw: String,

    @Column
    val followingCount: Int? = 0,

    @Column
    val postCount: Int? = 0,

    @Column
    val followCount: Int? = 0,

    @Column
    val profileImg: String? = null,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    val posts: MutableList<PostModel> = mutableListOf(),
)

data class UserResponse(
    val result: String,
    val errorMsg: String,
)