package com.kjh.myserver073.model.model

import com.kjh.myserver073.model.vo.UserAndPostsAndBookmarks

data class UserFollowModel(
    val myProfile    : UserAndPostsAndBookmarks,
    val targetProfile: UserAndPostsAndBookmarks
)