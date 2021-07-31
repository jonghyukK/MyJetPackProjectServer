package com.kjh.myserver073.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * myserver073
 * Class: TemplateModel
 * Created by mac on 2021/07/21.
 *
 * Description:
 */

@Entity
data class TemplateModel(

    @Id
    @GeneratedValue
    val id: Int,
    val templateName: String,
    val content: String
)