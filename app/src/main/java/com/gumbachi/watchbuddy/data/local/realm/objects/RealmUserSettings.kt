package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmUserSettings() : RealmObject {

    constructor(settings: UserSettings) : this() {
        cardStyle = settings.cardStyle.toString()
        scoreFormat = settings.scoreFormat.toString()
        movieSort = settings.movieSort.toString()
        showSort = settings.showSort.toString()
    }

    @PrimaryKey
    var id = 0

    var cardStyle = CardStyle.Normal.toString()
    var scoreFormat = ScoreFormat.Decimal.toString()
    var movieSort = Sort.ScoreDescending.toString()
    var showSort = Sort.ScoreDescending.toString()

    fun toUserSettings(): UserSettings = UserSettings(
        cardStyle = CardStyle.valueOf(cardStyle),
        scoreFormat = ScoreFormat.valueOf(scoreFormat),
        movieSort = Sort.valueOf(movieSort),
        showSort = Sort.valueOf(showSort),
    )

}
