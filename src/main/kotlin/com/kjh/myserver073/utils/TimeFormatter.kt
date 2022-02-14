package com.kjh.myserver073.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    fun makeDateTimeFormat(): String {
        val formatter = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formatter)
        val today = Calendar.getInstance().time

        return dateFormat.format(today)
    }
}