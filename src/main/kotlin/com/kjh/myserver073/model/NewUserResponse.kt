package com.kjh.myserver073.model

data class NewUserResponse(
        val result   : Boolean,
        val data     : Any? = null,
        val errorMsg : String? = null
)