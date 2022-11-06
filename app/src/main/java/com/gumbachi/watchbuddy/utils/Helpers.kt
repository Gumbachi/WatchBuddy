package com.gumbachi.watchbuddy.utils

import android.util.Log
import java.time.LocalDate

fun parseDate(dateString: String): LocalDate {
    return try {
        LocalDate.parse(dateString)
    } catch (e: Exception) {
        Log.w("Object", "Couldn't parse ($dateString) into a valid date")
        LocalDate.now()
    }
}