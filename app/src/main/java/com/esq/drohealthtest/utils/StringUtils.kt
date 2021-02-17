package com.esq.drohealthtest.utils

object StringUtils {

    fun getNumberOfItemsToString(number: Int): String {
        return if (number < 1) {
            "0 item(s)"
        } else {
            "$number item(s)"
        }
    }

    fun getPriceNumberToString(number: Int): String {
        return if (number < 1) {
            "₦0"
        } else {
            "₦$number"
        }
    }
}