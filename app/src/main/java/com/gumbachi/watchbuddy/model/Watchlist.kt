package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.utils.sortedBy

data class Watchlist<T : Media>(
    val entries: List<T> = emptyList(),
    val sort: Sort = Sort.Score,
    val order: SortOrder = SortOrder.Descending,
    val filter: (T) -> Boolean = { true }
) {
    private val categorizedMedia = entries
        .filter(filter)
        .sortedBy(sort, order)
        .groupBy { it.watchStatus }

    fun getListFor(status: WatchStatus) = categorizedMedia.getOrElse(status) {
        emptyList()
    }

    fun findByID(id: WatchBuddyID): T? = entries.find { it.watchbuddyID == id }

    fun contains(element: T) = entries.contains(element)
    fun contains(id: WatchBuddyID) = id in entries.map { it.watchbuddyID }
}
