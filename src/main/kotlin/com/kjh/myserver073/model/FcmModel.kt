package com.kjh.myserver073.model

/**
 * myserver073
 * Class: FcmModel
 * Created by mac on 2021/10/01.
 *
 * Description:
 */
data class FcmModel(
    val validate_only: Boolean,
    val message: Message
)

data class Message(
    val notification: Notification,
    val token: String,
)

data class Notification(
    val title: String,
    val body: String,
    val image: String
)