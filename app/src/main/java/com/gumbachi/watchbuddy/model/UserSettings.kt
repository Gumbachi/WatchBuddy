package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.ScoreFormat

data class UserSettings(
    val cardStyle: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Decimal
)
