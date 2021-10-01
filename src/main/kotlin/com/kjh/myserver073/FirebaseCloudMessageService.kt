package com.kjh.myserver073

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.kjh.myserver073.model.FcmModel
import com.kjh.myserver073.model.Message
import com.kjh.myserver073.model.Notification
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.apache.http.HttpHeaders
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

/**
 * myserver073
 * Class: FirebaseCloudMessageService
 * Created by mac on 2021/09/17.
 *
 * Description:
 */

@Service
class FirebaseCloudMessageService {
    private var objectMapper = ObjectMapper()

    fun sendMessageTo(
        targetToken: String,
        title: String,
        body: String
    ) {
        val client = OkHttpClient()

        val fcmMessage = FcmModel(
            validate_only = false,
            message = Message(
                token = targetToken,
                notification = Notification(
                    title = title,
                    body = body,
                    image = ""
                )
            )
        )

        val str = objectMapper.writeValueAsString(fcmMessage)

        val requestBody = RequestBody.create(
            contentType = "application/json; charset=utf-8".toMediaType(),
            str
        )

        val request = Request.Builder()
            .url(FCM_API_URL)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build()

        val res = client.newCall(request).execute()

        println(res.body.toString())
    }


    private fun getAccessToken(): String {
        val firebaseConfigPath = "firebase/myjetpackpractice-firebase-adminsdk-o5hlk-330b4acf82.json"

        val googleCredentials = GoogleCredentials
            .fromStream(ClassPathResource(firebaseConfigPath).inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))

        googleCredentials.refreshIfExpired()

        return googleCredentials.accessToken.tokenValue
    }

}