package com.gumbachi.watchbuddy.utils

import android.util.Log
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.Source
import java.time.LocalDate
import kotlin.math.roundToInt

fun String.parseWatchbuddyID(): Pair<Source, Int> {
    val split = split("|", limit = 2)
    return Source.valueOf(split.first()) to split.last().toInt()
}

fun String?.parseDateOrNow(): LocalDate {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        Log.w("Object", "Couldn't parse ($this) into a valid date. Returning ${LocalDate.now()}")
        LocalDate.now()
    }
}

fun String?.parseDateOrNull(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        Log.w("Object", "Couldn't parse ($this) into a valid date. Returning Null")
        null
    }
}

fun String.toInt(scoreFormat: ScoreFormat): Int {
    val trimmed = this.removePrefix(scoreFormat.prefix).removeSuffix(scoreFormat.suffix)

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
                this.toIntOrNull() == null -> false
                this.count() > 2 -> false
                this.toInt() !in 0..10 -> false
                else -> true
            }
        }
        ScoreFormat.Percentage -> {
            return when {
                this.toIntOrNull() == null -> false
                this.count() > 3 -> false
                this.toInt() !in 0..100 -> false
                else -> true
            }
        }
        ScoreFormat.Decimal -> {
            return when {
                this.toDoubleOrNull() == null -> false
                this.count() > 4 -> false
                this.toDouble() !in 0.0..10.0 -> false
                // Ensure only 1 decimal place
                this.contains('.') && (this.length - 1) - this.indexOf('.') > 1 -> false
                else -> true
            }
        }
    }
}
