package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort

data class UserSettings(
    val cardStyle: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Percentage,
    val movieSort: Sort = Sort.ScoreDescending,
    val showSort: Sort = Sort.ScoreDescending,
) {

    companion object {
        fun fromMap(data: Map<String, Any>): UserSettings {
            return UserSettings(
                cardStyle = CardStyle.valueOf(data["cardStyle"] as String),
                scoreFormat = ScoreFormat.valueOf(data["scoreFormat"] as String),
                movieSort = Sort.valueOf(data["movieSort"] as String),
                showSort = Sort.valueOf(data["showSort"] as String),
            )
        }
    }

}
