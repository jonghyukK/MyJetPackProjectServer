package com.kjh.myserver073.service

import com.kjh.myserver073.model.TemplateModel
import com.kjh.myserver073.repository.TemplateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * myserver073
 * Class: TemplateServiceImpl
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

@Service
class TemplateServiceImpl constructor(
    @Autowired private val templateRepository: TemplateRepository
): TemplateService {

    override fun getAllTemplates(): List<TemplateModel>? =
        templateRepository.findAllBy()

    override fun getTemplate(id: Int): TemplateModel? =
        templateRepository.findById(id).orElse(null)

    override fun getTemplateByName(name: String): TemplateModel? {
        return templateRepository.findByTemplateName(name)
    }

    @Transactional
    override fun saveTemplate(templateModel: TemplateModel): TemplateModel =
        templateRepository.save(templateModel)
}