package com.kjh.myserver073.controller

import com.kjh.myserver073.service.BookMarkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * myserver073
 * Class: BookMarkController
 * Created by mac on 2021/09/14.
 *
 * Description:
 */

@RestController
@RequestMapping
class BookMarkController {

    @Autowired
    private lateinit var bookMarkService: BookMarkService
}