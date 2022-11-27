package com.gumbachi.watchbuddy.model

import java.time.LocalDateTime

data class RecentSearch(
    val query: String,
    val resultCount: Int,
    val searchTime:LocalDateTime
)
