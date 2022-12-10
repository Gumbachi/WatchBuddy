package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.utils.sortedBy

data class Watchlist<T : Media>(
    val entries: List<T> = emptyList(),
    val sort: Sort = Sort.ScoreDescending
) {
    private val categorizedMedia = entries.sortedBy(sort).groupBy { it.watchStatus }

    fun getListFor(status: WatchStatus) = categorizedMedia.getOrElse(status) {
        emptyList()
    }

    fun findByID(id: WatchbuddyID): T? = entries.find { it.watchbuddyID == id }

    fun contains(element: T) = entries.contains(element)
    fun contains(id: WatchbuddyID) = id in entries.map { it.watchbuddyID }
}
