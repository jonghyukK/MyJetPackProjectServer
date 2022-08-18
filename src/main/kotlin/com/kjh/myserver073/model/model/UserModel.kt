package com.kjh.myserver073.model.model

import com.kjh.myserver073.model.entity.User

data class UserModel(
    val userId          : Int,
    val email           : String,
    val nickName        : String,
    val introduce       : String? = null,
    val followingCount  : Int = 0,
    val followCount     : Int = 0,
    val postCount       : Int = 0,
    val profileImg      : String? = null,
    val followList      : List<String> = listOf(),
    val followingList   : List<String> = listOf(),
    val isFollowing     : Boolean? = null,
    val posts           : List<PostModel> = listOf(),
    val bookmarks       : List<BookmarkModel> = listOf()
)

fun User.toModel() =
    UserModel(
        userId          = userId!!,
        email           = email,
        nickName        = nickName,
        introduce       = introduce,
        followingCount  = followingCount,
        followCount     = followCount,
        postCount       = postCount,
        profileImg      = profileImg,
        followList      = followList,
        followingList   = followingList,
        isFollowing     = isFollowing,
        posts           = posts.map { it.toModel() },
        bookmarks       = bookmarks.map { it.toModel() }
    )