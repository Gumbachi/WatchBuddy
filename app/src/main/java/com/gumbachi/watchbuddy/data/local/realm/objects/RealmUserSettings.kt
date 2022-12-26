package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.WatchbuddyRealmObject
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmUserSettings() : RealmObject, WatchbuddyRealmObject {

    @PrimaryKey
    var id = 0

    var cardStyle = CardStyle.Normal.toString()
    var scoreFormat = ScoreFormat.Percentage.toString()
    var showProgressOnPlannedMedia = false

    // Movie Related
    var movieSort = Sort.ScoreDescending.toString()
    var hiddenMovieStatuses =
        listOf(WatchStatus.Watching, WatchStatus.Repeating).map { it.toString() }.toRealmSet()

    // Show Related
    var showSort = Sort.ScoreDescending.toString()
    var hiddenShowStatuses = realmSetOf<String>()

    // Other
    var defaultSearchFilter: RealmMediaFilter? = RealmMediaFilter()


    override fun toWatchbuddyObject() = UserSettings(
        cardStyle = CardStyle.valueOf(cardStyle),
        scoreFormat = ScoreFormat.valueOf(scoreFormat),
        showProgressOnPlannedMedia = showProgressOnPlannedMedia,
        movieSort = Sort.valueOf(movieSort),
        hiddenMovieStatuses = hiddenMovieStatuses.map { WatchStatus.valueOf(it) }.toSet(),
        showSort = Sort.valueOf(showSort),
        hiddenShowStatuses = hiddenShowStatuses.map { WatchStatus.valueOf(it) }.toSet(),
        defaultSearchFilter = defaultSearchFilter?.toWatchbuddyObject()?: MediaFilter()
    )

}
