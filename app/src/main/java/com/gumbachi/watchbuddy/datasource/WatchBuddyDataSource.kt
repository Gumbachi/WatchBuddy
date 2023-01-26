package com.gumbachi.watchbuddy.datasource

import com.gumbachi.watchbuddy.model.interfaces.MediaDetails
import com.gumbachi.watchbuddy.model.interfaces.SearchResult

interface WatchBuddyDataSource {
    suspend fun searchMedia(query: String): List<SearchResult>
    suspend fun getShowDetails(id: Int): MediaDetails
    suspend fun getMovieDetails(id: Int): MediaDetails
}