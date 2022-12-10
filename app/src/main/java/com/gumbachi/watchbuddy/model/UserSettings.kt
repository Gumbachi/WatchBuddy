package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus

data class UserSettings(
    // General Settings
    val cardStyle: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Percentage,

    // Movie Related Settings
    val movieSort: Sort = Sort.ScoreDescending,
    val hiddenMovieStatuses: Set<WatchStatus> = setOf(WatchStatus.Watching, WatchStatus.Repeating),

    // Show Related Settings
    val showSort: Sort = Sort.ScoreDescending,
    val hiddenShowStatuses: Set<WatchStatus> = emptySet()
)
