package com.kjh.myserver073.model.common

import org.springframework.http.ResponseEntity

data class ApiResponse(
    val result  : Boolean,
    val data    : Any? = null,
    val errorMsg: String? = null
)

fun ApiResponse.toResponseEntity() = ResponseEntity.ok().body(this)