package com.gumbachi.watchbuddy.module.search

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBSearchResults
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBShow
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.model.SearchFilter
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.model.interfaces.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow

private const val TAG = "SearchRepository"

interface SearchRepository {

    // New
    suspend fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun getSavedMediaIDs(): Flow<List<WatchbuddyID>>
    suspend fun searchFor(query: String, filter: SearchFilter): List<SearchResult>
    suspend fun saveMedia(media: Media)

    // Old
    suspend fun generateBlankMedia(searchResult: SearchResult): Media
    suspend fun getRecentSearches(count: Int): List<RecentSearch>
    suspend fun clearSearchHistory()
}


class SearchRepositoryImpl(
    private val tmdb: TMDBApi,
    private val db: WatchbuddyDatabase
) : SearchRepository {

    private val searchHistory = mutableListOf<RecentSearch>()
    private val resultsFlow = emptyFlow<List<SearchResult>>()

    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting Settings Flow")
        return db.getUserSettingsFlow().distinctUntilChanged()
    }

    override suspend fun getSavedMediaIDs(): Flow<List<WatchbuddyID>> {
        Log.d(TAG, "Getting Saved IDs Flow")
        return db.getSavedMediaIDs().distinctUntilChanged()
    }

    override suspend fun searchFor(query: String, filter: SearchFilter): List<SearchResult> {

        val accumulatedResults = mutableListOf<SearchResult>()

        if (filter.includeTMDBMovies) {
            val tmdbMovieResults = tmdb.searchMovies(query).toTMDBSearchResults()
            accumulatedResults += tmdbMovieResults
        }

        if (filter.includeTMDBShows) {
            val tmdbShowResults = tmdb.searchShows(query).toTMDBSearchResults()
            accumulatedResults += tmdbShowResults
        }

        //TODO Add Anilist

        return accumulatedResults.sortedByDescending { it.weight() }.toList()
    }

    override suspend fun saveMedia(media: Media) {
        when (media) {
            is Movie -> db.addMovie(media)
            is Show -> db.addShow(media)
        }
    }

    override suspend fun generateBlankMedia(searchResult: SearchResult): Media {
        return when (searchResult.watchbuddyID.source) {
            Source.TMDBMovie -> tmdb.getMovieDetails(searchResult.id).toTMDBMovie()
            Source.TMDBShow -> tmdb.getShowDetails(searchResult.id).toTMDBShow()
            else -> TODO("Fill remaining branches")
        }
    }

    override suspend fun clearSearchHistory() {
        return searchHistory.clear()
    }

    override suspend fun getRecentSearches(count: Int): List<RecentSearch> {
        return searchHistory.take(count)
    }
}