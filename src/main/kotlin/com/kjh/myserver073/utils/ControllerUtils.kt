package com.kjh.myserver073.utils

import com.kjh.myserver073.BASE_IMAGE_URL
import com.kjh.myserver073.IMAGE_SAVE_FOLDER
import com.kjh.myserver073.model.PostModel
import com.kjh.myserver073.model.UserModel
import com.kjh.myserver073.model.UserVo
import org.springframework.web.multipart.MultipartFile
import java.io.File

/**
 * myserver073
 * Class: ControllerUtils
 * Created by mac on 2021/09/14.
 *
 * Description:
 */


fun savePostData(
    email       : String,
    profileImg  : String? = null,
    content     : String,
    files       : List<MultipartFile>,
    address_name: String,
    category_group_code: String,
    category_group_name: String,
    category_name: String,
    phone       : String,
    place_name  : String,
    place_url   : String,
    road_address_name: String,
    x           : String,
    y           : String,
): PostModel {
    val imgUrls = mutableListOf<String>()
    val savePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER

    if (!File(savePath).exists()) {
        try {
            File(savePath).mkdir()
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    for (file in files) {
        val originFileName = file.originalFilename ?: "Empty"
        val filePath = "$savePath/$originFileName"
        file.transferTo(File(filePath))

        imgUrls.add("$BASE_IMAGE_URL${file.originalFilename}")
    }

    return PostModel(
        postId = null,
        cityCategory = LocationUtil.makeCityCategory(address_name),
        imageUrl = imgUrls,
        email    = email,
        content  = content,
        address_name = address_name,
        category_group_code = category_group_code,
        category_group_name = category_group_name,
        category_name = category_name,
        phone = phone,
        placeName = place_name,
        place_url = place_url,
        road_address_name = road_address_name,
        x = x,
        y = y,
        profileImg = profileImg
    )
}

fun saveProfileFileGetImagePath(
    email: String,
    file: MultipartFile
): String {
    val profileSavePath = System.getProperty("user.dir") + IMAGE_SAVE_FOLDER
    if (!File(profileSavePath).exists()) {
        try {
            File(profileSavePath).mkdir()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val originFileName = "${email}_${file.originalFilename}"
    val filePath = "$profileSavePath/$originFileName"
    file.transferTo(File(filePath))

    return "$BASE_IMAGE_URL${originFileName}"
}