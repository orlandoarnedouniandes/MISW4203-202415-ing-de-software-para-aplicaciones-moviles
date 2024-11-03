package com.mobileapp.mymobileapp.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object DateUtils {
    fun extractYear(dateString: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return date?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it
            calendar.get(Calendar.YEAR).toString()
        }
    }
}

