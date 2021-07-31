package com.kjh.myserver073.controller

import com.kjh.myserver073.model.TemplateModel
import com.kjh.myserver073.service.TemplateService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * myserver073
 * Class: TemplateController
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

@RestController
@RequestMapping
class TemplateController {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var templateService: TemplateService

    @GetMapping("/templates")
    private fun getTemplates(): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(templateService.getAllTemplates())
    }

    @GetMapping("/template/{id}")
    private fun getTemplateById(@PathVariable id: Int): ResponseEntity<Any> {
        return ResponseEntity
            .ok()
            .body(templateService.getTemplate(id))
    }

    @GetMapping("/template")
    private fun getTemplateByName(@RequestParam(value = "name") name: String): ResponseEntity<Any?> {
        return ResponseEntity
            .ok()
            .body(templateService.getTemplateByName(name))
    }

    @PostMapping("/signup")
    private fun postTemplate(@RequestBody templateModel: TemplateModel): ResponseEntity<Any> {

        val str = templateService.getTemplate(2)

        logger.info("""
            test test test :  ${str}
        """.trimIndent())

        if (str != null) {
            return ResponseEntity
                .ok()
                .body(TemplateModel(
                    id = 35,
                    templateName = "이미 존재합니다",
                    content = "이미 존재해"
                ))
        }

        templateService.saveTemplate(templateModel)
        return ResponseEntity
            .ok()
            .body(templateModel)
    }


}