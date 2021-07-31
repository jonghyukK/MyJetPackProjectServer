package com.kjh.myserver073.repository

import com.kjh.myserver073.model.TemplateModel
import org.springframework.data.repository.CrudRepository

/**
 * myserver073
 * Class: TemplateRepository
 * Created by mac on 2021/07/21.
 *
 * Description:
 */
interface TemplateRepository: CrudRepository<TemplateModel, Int> {

    fun findByTemplateName(templateName: String): TemplateModel?

    fun findAllBy(): List<TemplateModel>?
}