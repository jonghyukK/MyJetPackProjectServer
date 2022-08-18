package com.kjh.myserver073.controller

import com.kjh.myserver073.model.common.ApiResponse
import com.kjh.myserver073.model.common.toResponseEntity
import com.kjh.myserver073.service.BookmarkService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BookmarkController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var bookmarkService: BookmarkService

    /***************************************************
     *
     *  [PUT] Update Bookmarks.
     *
     ***************************************************/
    @PutMapping("bookmarks")
    private fun updateBookmarks(
        @RequestParam("myEmail"  ) myEmail  : String,
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<ApiResponse> =
        try {
            ApiResponse(
                result = true,
                data = bookmarkService.updateBookmarks(myEmail, placeName)
            ).toResponseEntity()
        } catch (e: Exception) {
            ApiResponse(
                result = false,
                errorMsg = "북마크 업데이트에 실패하였습니다."
            ).toResponseEntity()
        }
}