package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmUserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.RealmSavable
import io.realm.kotlin.ext.toRealmSet

data class UserSettings(
    // General Settings
    val cardStyle: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Percentage,
    val showProgressOnPlannedMedia: Boolean = false,

    // Movie Related Settings
    val movieSort: Sort = Sort.ScoreDescending,
    val hiddenMovieStatuses: Set<WatchStatus> = setOf(WatchStatus.Watching, WatchStatus.Repeating),

    // Show Related Settings
    val showSort: Sort = Sort.ScoreDescending,
    val hiddenShowStatuses: Set<WatchStatus> = emptySet(),

    val defaultSearchFilter: MediaFilter = MediaFilter()

): RealmSavable {

    override fun toRealmObject(): RealmUserSettings {
        val settings = this
        return RealmUserSettings().apply {
            cardStyle = settings.cardStyle.toString()
            scoreFormat = settings.scoreFormat.toString()
            showProgressOnPlannedMedia = settings.showProgressOnPlannedMedia
            movieSort = settings.movieSort.toString()
            hiddenMovieStatuses = settings.hiddenMovieStatuses.map { it.toString() }.toRealmSet()
            showSort = settings.showSort.toString()
            hiddenShowStatuses = settings.hiddenShowStatuses.map { it.toString() }.toRealmSet()
            defaultSearchFilter = settings.defaultSearchFilter.toRealmObject()
        }
    }
}

