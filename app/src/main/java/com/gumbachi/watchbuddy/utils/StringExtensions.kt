package com.gumbachi.watchbuddy.utils

import android.util.Log
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import kotlinx.datetime.LocalDate
import kotlin.math.roundToInt

fun String.parseDateOrNull(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        Log.w("DateParsing", "Couldn't parse ($this) into a valid date. Returning Null")
        null
    }
}

fun String.toInt(scoreFormat: ScoreFormat): Int {
    val trimmed = removePrefix(scoreFormat.prefix).removeSuffix(scoreFormat.suffix)

    if (trimmed.isBlank()) {
        return 0
    }

    return  when (scoreFormat) {
        ScoreFormat.Percentage -> trimmed.toInt()
        ScoreFormat.Integer -> trimmed.toInt() * 10
        ScoreFormat.Decimal -> (trimmed.toDouble() * 10).roundToInt()
    }
}

fun String.validate(scoreFormat: ScoreFormat): Boolean {
    when (scoreFormat) {
        ScoreFormat.Integer -> {
            return when {
                toIntOrNull() == null -> false
                count() > 2 -> false
                toInt() !in 0..10 -> false
                else -> true
            }
        }
        ScoreFormat.Percentage -> {
            return when {
                toIntOrNull() == null -> false
                count() > 3 -> false
                toInt() !in 0..100 -> false
                else -> true
            }
        }
        ScoreFormat.Decimal -> {
            return when {
                toDoubleOrNull() == null -> false
                count() > 4 -> false
                toDouble() !in 0.0..10.0 -> false
                // Ensure only 1 decimal place
                contains('.') && (length - 1) - indexOf('.') > 1 -> false
                else -> true
            }
        }
    }
}
