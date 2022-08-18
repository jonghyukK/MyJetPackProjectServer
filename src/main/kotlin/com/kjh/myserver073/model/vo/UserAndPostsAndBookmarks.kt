package com.kjh.myserver073.model.vo

import com.kjh.myserver073.model.model.BookmarkModel
import com.kjh.myserver073.model.model.PostModel

data class UserAndPostsAndBookmarks(
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
    val posts           : List<PostModel>,
    val bookMarks       : List<BookmarkModel>
)