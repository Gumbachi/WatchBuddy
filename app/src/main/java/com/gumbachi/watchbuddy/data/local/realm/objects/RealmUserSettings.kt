package com.gumbachi.watchbuddy.data.local.realm.objects

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
    var hiddenMovieStatuses =
        listOf(WatchStatus.Watching, WatchStatus.Repeating).map { it.toString() }.toRealmSet()

    // Show Related
    var showSort = Sort.ScoreDescending.toString()
    var hiddenShowStatuses = realmSetOf<String>()

    // Other
    var defaultSearchFilter: RealmMediaFilter? = RealmMediaFilter()

}
