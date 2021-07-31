package com.kjh.myserver073.service

import com.kjh.myserver073.model.TemplateModel
import org.springframework.stereotype.Service

/**
 * myserver073
 * Class: TemplateService
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

@Service
interface TemplateService {

    fun getAllTemplates(): List<TemplateModel>?

    fun getTemplate(id: Int): TemplateModel?

    fun saveTemplate(templateModel: TemplateModel): TemplateModel

    fun getTemplateByName(name: String): TemplateModel?
}