package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmUserSettings() : RealmObject {

    @PrimaryKey
    var id = 0

    var cardStyle = CardStyle.Normal.toString()
    var scoreFormat = ScoreFormat.Percentage.toString()

    // Movie Related
    var movieSort = Sort.ScoreDescending.toString()
    var hiddenMovieStatuses = listOf(WatchStatus.Watching, WatchStatus.Repeating).map{ it.toString() }.toRealmSet()

    // Show Related
    var showSort = Sort.ScoreDescending.toString()
    var hiddenShowStatuses = realmSetOf<String>()

    fun toUserSettings(): UserSettings = UserSettings(
        cardStyle = CardStyle.valueOf(cardStyle),
        scoreFormat = ScoreFormat.valueOf(scoreFormat),
        movieSort = Sort.valueOf(movieSort),
        hiddenMovieStatuses = hiddenMovieStatuses.map { WatchStatus.valueOf(it) }.toSet(),
        showSort = Sort.valueOf(showSort),
        hiddenShowStatuses = hiddenShowStatuses.map { WatchStatus.valueOf(it) }.toSet()
    )

    companion object {
        infix fun from(settings: UserSettings) = RealmUserSettings().apply {
            cardStyle = settings.cardStyle.toString()
            scoreFormat = settings.scoreFormat.toString()
            movieSort = settings.movieSort.toString()
            hiddenMovieStatuses = settings.hiddenMovieStatuses.map { it.toString() }.toRealmSet()
            showSort = settings.showSort.toString()
            hiddenShowStatuses = settings.hiddenShowStatuses.map { it.toString() }.toRealmSet()
        }
    }

}
