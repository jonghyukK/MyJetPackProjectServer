package com.kjh.myserver073.model

import org.jetbrains.annotations.NotNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * myserver073
 * Class: UserModel
 * Created by mac on 2021/07/27.
 *
 * Description:
 */

@Entity
data class UserModel(

    @Id
    @GeneratedValue
    val id: Int,
    val email: String,
    val pw: String,
    val pwConfirm: String,
)

data class UserResponse(
    val result: String,
    val errorMsg: String,
)