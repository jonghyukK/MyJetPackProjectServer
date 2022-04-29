package com.kjh.myserver073.controller

import com.kjh.myserver073.model.BookMarkResponse
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
     *  [GET] GET Bookmarks.
     *
     ***************************************************/
    @GetMapping("bookmarks")
    private fun getBookmarks(
        @RequestParam("myEmail") myEmail: String
    ): ResponseEntity<BookMarkResponse> {
        try {
            return ResponseEntity
                .ok()
                .body(
                    BookMarkResponse(
                        result = true,
                        data = bookmarkService.getBookmarks(myEmail)
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    BookMarkResponse(
                        result = false,
                        errorMsg = "북마크 가져오기에 실패하였습니다."
                    )
                )
        }
    }


    /***************************************************
     *
     *  [PUT] Update Bookmarks.
     *
     ***************************************************/
    @PutMapping("bookmarks")
    private fun updateBookmarks(
        @RequestParam("myEmail"  ) myEmail: String,
        @RequestParam("postId"   ) postId: Int,
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<BookMarkResponse> {
        try {
            return ResponseEntity
                .ok()
                .body(
                    BookMarkResponse(
                        result = true,
                        data = bookmarkService.updateBookmarks(myEmail, postId, placeName)
                    )
                )
        } catch (e: Exception) {
            return ResponseEntity
                .ok()
                .body(
                    BookMarkResponse(
                        result = false,
                        errorMsg = "북마크 업데이트에 실패하였습니다."
                    )
                )
        }
    }
}