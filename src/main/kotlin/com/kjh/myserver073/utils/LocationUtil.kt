package com.kjh.myserver073.utils

import com.kjh.myserver073.countryList

/**
 * myserver073
 * Class: LocationUtil
 * Created by mac on 2021/08/26.
 *
 * Description:
 */
object LocationUtil {
    fun makeCityCategory(address: String): String {
        for (city in countryList) {
            if (address.contains(city)) {
                return city
            }
        }
        return ""
    }
}