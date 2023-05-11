package com.gumbachi.watchbuddy.module.search

import android.util.Log
import com.gumbachi.watchbuddy.database.WatchbuddyDatabase
import com.gumbachi.watchbuddy.datasource.anilist.AnilistAPI
import com.gumbachi.watchbuddy.datasource.tmdb.TMDBApi
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.*
import com.gumbachi.watchbuddy.model.settings.UserSettings
import kotlinx.coroutines.flow.*

private const val TAG = "SearchRepository"

interface SearchRepository {

    // New
    suspend fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun getSavedMediaIDs(): Flow<List<WatchBuddyID>>
    suspend fun searchFor(query: String): List<SearchResult>
    suspend fun saveMedia(media: Media)
    suspend fun updateMedia(media: Media)
    suspend fun deleteMedia(media: Media)
    suspend fun findMedia(searchResult: SearchResult): Media?

    // Recents
    suspend fun addToRecents(item: Cardable)
    suspend fun getRecentsFlow(): StateFlow<List<Cardable>>

    // Old
    suspend fun generateBlankMedia(searchResult: SearchResult): Media
    suspend fun getRecentSearches(count: Int): List<RecentSearch>
    suspend fun clearSearchHistory()
}


class SearchRepositoryImpl(
    private val tmdb: TMDBApi,
    private val anilist: AnilistAPI,
    private val db: WatchbuddyDatabase
) : SearchRepository {

    private val searchHistory = mutableListOf<RecentSearch>()
    private val resultsFlow = emptyFlow<List<SearchResult>>()
    private val recentItems = MutableStateFlow(emptyList<Cardable>())

    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting Settings Flow")
        return db.getUserSettingsFlow().distinctUntilChanged()
    }

    override suspend fun getSavedMediaIDs(): Flow<List<WatchBuddyID>> {
        Log.d(TAG, "Getting Saved IDs Flow")
        return db.getSavedMediaIDs().distinctUntilChanged()
    }

    override suspend fun searchFor(query: String): List<SearchResult> {

        val settings = db.getUserSettingsFlow().first()
        val results = mutableListOf<SearchResult>()

        if (settings.tmdb.enabled) {
            results.addAll(tmdb.searchMovies(query) + tmdb.searchShows(query))
        }

        if (settings.anilist.enabled) {
            results.addAll(anilist.searchMedia(query))
        }

        return results.sortedByDescending { it.weight() }.toList()
    }

    override suspend fun saveMedia(media: Media) {
        when (media) {
            is Movie -> db.addMovie(media)
            is Show -> db.addShow(media)
        }
    }

    override suspend fun updateMedia(media: Media) {
        when (media) {
            is Movie -> db.updateMovie(media)
            is Show -> db.updateShow(media)
        }
    }

    override suspend fun deleteMedia(media: Media) {
        when (media) {
            is Movie -> db.removeMovie(media)
            is Show -> db.removeShow(media)
        }
    }

    override suspend fun findMedia(searchResult: SearchResult): Media? {
        return db.findMedia(searchResult.watchbuddyID)

    }

    override suspend fun generateBlankMedia(searchResult: SearchResult): Media {
        return when (searchResult.watchbuddyID.source) {
            Source.TMDBMovie -> tmdb.getBlankMovie(searchResult.id)
            Source.TMDBShow -> tmdb.getBlankShow(searchResult.id)
            Source.AnilistMovie -> anilist.getBlankMovie(searchResult.id)
            Source.AnilistShow -> anilist.getBlankShow(searchResult.id)
            Source.CustomMovie, Source.CustomShow -> TODO("Add Support for custom items")
        }
    }


    override suspend fun clearSearchHistory() {
        return searchHistory.clear()
    }

    override suspend fun getRecentSearches(count: Int): List<RecentSearch> {
        return searchHistory.take(count)
    }

    override suspend fun addToRecents(item: Cardable) {
        recentItems.update {
            val newList = it.toMutableList()
            newList.add(0, item)
            newList.distinctBy { it.watchbuddyID }.take(10)
        }
    }

    override suspend fun getRecentsFlow(): StateFlow<List<Cardable>> {
        return recentItems.asStateFlow()
    }
}