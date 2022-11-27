package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat

fun Int.toString(format: ScoreFormat, decorated: Boolean = false): String {
    val num =  when (format) {
        ScoreFormat.Integer -> "${this / 10}"
        ScoreFormat.Decimal -> "${this/10.0}"
        ScoreFormat.Percentage -> "$this"
    }

    if (!decorated) return num
    return "${format.prefix}$num${format.suffix}"
}
