package com.gumbachi.watchbuddy.model.enums.configuration

enum class ScoreFormat(
    val prefix: String = "",
    val suffix: String = "",
    val example: String = "",
    val changeAmount: Int = 1
) {
    Integer(example = "8", changeAmount = 10),
    Decimal(example = "8.3"),
    Percentage(example = "83%", suffix = "%");
}


