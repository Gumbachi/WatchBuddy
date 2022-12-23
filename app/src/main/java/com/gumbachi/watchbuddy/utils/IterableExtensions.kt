package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.interfaces.Sortable

fun <T: Sortable> Iterable<T>.sortedBy(mode: Sort): List<T> {
    return when (mode) {
        Sort.ScoreAscending -> this.sortedBy { it.userScore }
        Sort.ScoreDescending -> this.sortedByDescending { it.userScore }
        Sort.ReleaseDateAscending -> this.sortedBy { it.releaseDate }
        Sort.ReleaseDateDescending -> this.sortedByDescending { it.releaseDate }
        Sort.Alphabetical -> this.sortedBy { it.title }
        Sort.ReleaseStatus -> this.sortedBy { it.releaseStatus.name }
        Sort.RecentlyUpdated -> this.sortedBy { it.lastUpdate }
    }
}
