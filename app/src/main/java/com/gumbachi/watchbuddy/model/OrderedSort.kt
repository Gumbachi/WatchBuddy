package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.configuration.SortOrder

data class OrderedSort(
    val sort: Sort,
    val order: SortOrder
)
