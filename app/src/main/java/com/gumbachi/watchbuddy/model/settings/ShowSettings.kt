package com.gumbachi.watchbuddy.model.settings

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmSet

data class ShowSettings(
    val sort: Sort = Sort.Score,
    val sortOrder: SortOrder = SortOrder.Descending,
    val hiddenStatuses: Set<WatchStatus> = emptySet()
) {

    fun toRealmShowSettings() = RealmShowSettings(

    )

}

class RealmShowSettings(
    private var sort: String = Sort.Score.name,
    private var sortOrder: String = SortOrder.Descending.name,
    private var hiddenStatuses: RealmSet<String> = realmSetOf()
) : EmbeddedRealmObject {

    constructor(): this(sort = Sort.Score.name)

    fun toShowSettings() = ShowSettings(
        sort = Sort.valueOf(sort),
        sortOrder = SortOrder.valueOf(sortOrder),
        hiddenStatuses = hiddenStatuses.map { WatchStatus.valueOf(it) }.toSet()
    )

}

