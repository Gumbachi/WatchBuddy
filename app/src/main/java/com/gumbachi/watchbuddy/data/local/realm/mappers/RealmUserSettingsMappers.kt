package com.gumbachi.watchbuddy.data.local.realm.mappers

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmUserSettings
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import io.realm.kotlin.ext.toRealmSet

fun RealmUserSettings.toUserSettings(): UserSettings = UserSettings(
    cardStyle = CardStyle.valueOf(cardStyle),
    scoreFormat = ScoreFormat.valueOf(scoreFormat),
    movieSort = Sort.valueOf(movieSort),
    hiddenMovieStatuses = hiddenMovieStatuses.map { WatchStatus.valueOf(it) }.toSet(),
    showSort = Sort.valueOf(showSort),
    hiddenShowStatuses = hiddenShowStatuses.map { WatchStatus.valueOf(it) }.toSet(),
    defaultSearchFilter = defaultSearchFilter?.toMediaFilter() ?: MediaFilter()
)

fun UserSettings.toRealmUserSettings(): RealmUserSettings {
    val settings = this
    return RealmUserSettings().apply {
        cardStyle = settings.cardStyle.toString()
        scoreFormat = settings.scoreFormat.toString()
        movieSort = settings.movieSort.toString()
        hiddenMovieStatuses = settings.hiddenMovieStatuses.map { it.toString() }.toRealmSet()
        showSort = settings.showSort.toString()
        hiddenShowStatuses = settings.hiddenShowStatuses.map { it.toString() }.toRealmSet()
        defaultSearchFilter = settings.defaultSearchFilter.toRealmMediaFilter()
    }
}