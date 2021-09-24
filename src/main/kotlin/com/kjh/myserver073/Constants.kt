package com.kjh.myserver073

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions


/**
 * myserver073
 * Class: Constants
 * Created by mac on 2021/08/14.
 *
 * Description:
 */

const val BASE_IMAGE_URL = "http://192.168.219.105/images/"
const val IMAGE_SAVE_FOLDER = "/my_images"

val countryList = listOf("전국", "서울", "경기", "인천", "충북", "충남", "경북", "경남", "전북", "전남", "강원")



data class FcmMessage(
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