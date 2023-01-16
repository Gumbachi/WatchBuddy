package com.gumbachi.watchbuddy.model.settings

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.ext.toRealmSet
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmSet

data class MovieSettings(
    val sort: Sort = Sort.Score,
    val sortOrder: SortOrder = SortOrder.Descending,
    val hiddenStatuses: Set<WatchStatus> = setOf(WatchStatus.Watching, WatchStatus.Repeating)
) {

    fun toRealmMovieSettings() = RealmMovieSettings(
        sort = sort.name,
        sortOrder = sortOrder.name,
        hiddenStatuses = hiddenStatuses.map { it.name }.toRealmSet()
    )

}

class RealmMovieSettings(
    private var sort: String = Sort.Score.name,
    private var sortOrder: String = SortOrder.Descending.name,
    private var hiddenStatuses: RealmSet<String> = realmSetOf(
        WatchStatus.Watching.name,
        WatchStatus.Repeating.name
    )
) : EmbeddedRealmObject {

    constructor(): this(sort = Sort.Score.name)

    fun toMovieSettings() = MovieSettings(
        sort = Sort.valueOf(sort),
        sortOrder = SortOrder.valueOf(sortOrder),
        hiddenStatuses = hiddenStatuses.map { WatchStatus.valueOf(it) }.toSet()
    )

}


