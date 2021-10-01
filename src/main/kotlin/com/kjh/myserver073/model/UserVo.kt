package com.kjh.myserver073.model

/**
 * myserver073
 * Class: UserVo
 * Created by mac on 2021/08/26.
 *
 * Description:
 */
data class UserVo(
    val userId   : Int?,
    val email    : String,
    val pw       : String,
    val postCount: Int? = 0,
    val followingCount: Int? = 0,
    val followCount   : Int? = 0,
    val profileImg    : String? = null,
    val bookMarks     : List<PostModel> = listOf(),
    val posts         : Map<String, List<PostModel>> = mapOf(),
)