package com.gumbachi.watchbuddy.model.enums

sealed class ScoreFormat(
    val prefix: String = "",
    val suffix: String = "",
    val example: String = ""
) {
    object Integer : ScoreFormat(example = "8") {
        override fun toString() = "Integer"
    }
    object Decimal : ScoreFormat(example = "8.3") {
        override fun toString() = "Decimal"
    }
    object Percentage : ScoreFormat(suffix = "%", example = "83%") {
        override fun toString() = "Percentage"
    }

    companion object {
        fun values(): List<ScoreFormat> {
            return listOf(
                Decimal,
                Integer,
                Percentage
            )
        }
    }
}

fun Double.format(scoreFormat: ScoreFormat): String {
    return  when (scoreFormat) {
        is ScoreFormat.Integer -> "${this.toInt()}"
        is ScoreFormat.Decimal -> String.format("%.1f", this)
        is ScoreFormat.Percentage -> "${(this * 10).toInt()}%"
    }
}

fun String.format(scoreFormat: ScoreFormat): Double {
    val trimmed = this.removePrefix(scoreFormat.prefix).removeSuffix(scoreFormat.suffix)
    return  when (scoreFormat) {
        is ScoreFormat.Integer -> this.toDouble()
        is ScoreFormat.Decimal -> this.toDouble()
        is ScoreFormat.Percentage -> this.toDouble() / 10.0
    }
}

fun String.validate(scoreFormat: ScoreFormat): Boolean {
    when (scoreFormat) {
        is ScoreFormat.Integer -> {
            return when {
                this.toIntOrNull() == null -> false
                this.count() > 2 -> false
                this.toInt() !in 0..10 -> false
                else -> true
            }
        }
        is ScoreFormat.Percentage -> {
            return when {
                this.toIntOrNull() == null -> false
                this.count() > 3 -> false
                this.toInt() !in 0..100 -> false
                else -> true
            }
        }
        is ScoreFormat.Decimal -> {
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
