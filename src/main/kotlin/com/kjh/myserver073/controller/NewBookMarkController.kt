package com.kjh.myserver073.controller

import com.kjh.myserver073.model.UpdateBookMarkResponse
import com.kjh.myserver073.model.entity.NewBookMarkModel
import com.kjh.myserver073.model.entity.NewUserModel
import com.kjh.myserver073.service.NewBookMarkService
import com.kjh.myserver073.service.NewUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping
class NewBookMarkController {

    @Autowired
    private lateinit var bookMarkService: NewBookMarkService

    @Autowired
    private lateinit var userService: NewUserService


    /***************************************************
     *
     *  [PUT] Update BookMark.
     *
     ***************************************************/
    @PutMapping("/bookmark")
    private fun updateBookmark(
        @RequestParam("email") email: String,
        @RequestParam("postId") postId: Int,
        @RequestParam("placeName") placeName: String
    ): ResponseEntity<Any> {
        val user = userService.getMyUser(email)!!

        val bookMarkItem = user.bookMarks.find { it.placeName == placeName }

        return if (bookMarkItem == null)
            addBookmark(user, postId, placeName)
        else
            deleteBookMark(user, postId, placeName)
    }

    private fun addBookmark(
        user: NewUserModel,
        postId: Int,
        placeName: String
    ): ResponseEntity<Any> {

        val newBookMark = NewBookMarkModel(
            bookmarkId = null,
            userId = user.userId!!,
            postId = postId,
            placeName = placeName
        )

        val newUser = userService.createUser(user.copy(
            bookMarks = user.bookMarks + newBookMark
        ))

        return ResponseEntity
            .ok()
            .body(
                UpdateBookMarkResponse(
                    result = true,
                    bookmarks = newUser.bookMarks
                )
            )
    }

    private fun deleteBookMark(
        user: NewUserModel,
        postId: Int,
        placeName: String
    ): ResponseEntity<Any> {

        val newUser = userService.createUser(
            user.copy(bookMarks = user.bookMarks.filter { it.placeName != placeName })
        )

        bookMarkService.findByUserId(user.userId!!).find { it.placeName == placeName }?.let {
            bookMarkService.deleteByBookmarkId(it.bookmarkId!!)
        }

        return ResponseEntity
            .ok()
            .body(
                UpdateBookMarkResponse(
                    result = true,
                    bookmarks = newUser.bookMarks
                )
            )
    }
}


//    /***************************************************
//     *
//     * [PUT] Update User Bookmark List.
//     *
//     ***************************************************/
//    @PutMapping("/user/bookmark")
//    private fun updateBookMarkUser(
//        @RequestParam("email"    ) email    : String,
//        @RequestParam("postId"   ) postId   : Int,
//        @RequestParam("placeName") placeName: String
//    ): ResponseEntity<Any> {
//        val prevUser = userService.getUserByEmail(email)!!
//
//        val bookMarkItem = prevUser.bookMarks.find { it.postId == postId }
//
//        if (bookMarkItem == null) {
//            val updatedUser = with(prevUser) {
//                bookMarks += BookMarkModel(
//                    bookmarkId  = 1000,
//                    userId      = prevUser.userId!!,
//                    postId      = postId,
//                    placeName   = placeName
//                )
//                copy(bookMarks = bookMarks).run {
//                    userService.createUser(this)
//                }
//            }
//
//            fcmService.sendMessageTo(
//                updatedUser.token,
//                placeName,
//                "북마크에 $placeName 추가되었습니다.")
//
//            return ResponseEntity
//                .ok()
//                .body(toUserVo(updatedUser))
//        } else {
//
//            val updateUser = prevUser.copy(
//                bookMarks = prevUser.bookMarks.filter { it.postId != postId }.toMutableList()
//            ).run {
//                userService.createUser(this)
//            }
//
//            bookMarkService.deleteByBookmarkId(bookMarkItem.bookmarkId!!)
//
//            return ResponseEntity
//                .ok()
//                .body(toUserVo(updateUser))
//        }
//    }
//
