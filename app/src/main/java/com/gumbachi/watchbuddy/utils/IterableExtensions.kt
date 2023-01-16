package com.gumbachi.watchbuddy.utils

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder
import com.gumbachi.watchbuddy.model.interfaces.Sortable

fun <T : Sortable> Iterable<T>.sortedBy(mode: Sort, order: SortOrder): List<T> {
    return when (mode) {
        Sort.Score -> when (order) {
            SortOrder.Ascending -> sortedBy { it.userScore }
            SortOrder.Descending -> sortedByDescending { it.userScore }
        }
        Sort.ReleaseDate -> when (order) {
            SortOrder.Ascending -> sortedBy { it.releaseDate }
            SortOrder.Descending -> sortedByDescending { it.userScore }
        }
        Sort.Name -> when (order) {
            SortOrder.Ascending -> sortedBy { it.title }
            SortOrder.Descending -> sortedByDescending { it.title }
        }
        Sort.ReleaseStatus -> when (order) {
            SortOrder.Ascending -> sortedBy { it.releaseStatus }
            SortOrder.Descending -> sortedByDescending { it.releaseStatus }
        }
        Sort.LastUpdated -> when (order) {
            SortOrder.Ascending -> sortedBy { it.lastUpdate }
            SortOrder.Descending -> sortedByDescending { it.lastUpdate }
        }
    }
}
