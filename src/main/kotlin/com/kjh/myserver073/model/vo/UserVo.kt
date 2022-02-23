package com.kjh.myserver073.model.vo

data class UserVo(
    val userId: Int,
    val email: String,
    val nickName: String,
    val introduce: String? = null,
    val followingCount: Int = 0,
    val followCount: Int = 0,
    val postCount: Int = 0,
    val profileImg: String? = null,
    val followList: List<String> = listOf(),
    val followingList: List<String> = listOf(),
    val posts: List<PostVo> = listOf(),
    val bookMarks: List<PostVo> = listOf(),
    val isFollowing: Boolean? = null
)