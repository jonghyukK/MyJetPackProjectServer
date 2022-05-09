package com.kjh.myserver073.model

data class UserResponse(
        val result   : Boolean,
        val data     : Any? = null,
        val errorMsg : String? = null
)