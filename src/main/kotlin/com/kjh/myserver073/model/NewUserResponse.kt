package com.kjh.myserver073.model

import com.kjh.myserver073.model.entity.NewPostModel

data class NewUserResponse(
        val result   : Boolean,
        val data     : Any? = null,
        val errorMsg : String? = null
)

data class User(
        val userId      : Int,
        val email       : String,
        val nickName    : String,
        val profileImg  : String? = null,
        val postCount      : Int = 0,
        val followingCount : Int = 0,
        val followCount    : Int? = 0,
        val introduce   : String? = null,
        val isFollowing : Boolean? = null,
        val posts       : List<NewPostModel> = listOf()
)