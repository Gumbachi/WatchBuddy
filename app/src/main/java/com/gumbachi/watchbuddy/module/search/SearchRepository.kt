package com.gumbachi.watchbuddy.module.search

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.data.remote.anilist.AnilistAPI
import com.gumbachi.watchbuddy.data.remote.anilist.mappers.toAnilistAnimeSearchResults
import com.gumbachi.watchbuddy.data.remote.anilist.mappers.toAnilistMovie
import com.gumbachi.watchbuddy.data.remote.anilist.mappers.toAnilistShow
import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBSearchResults
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBShow
import com.gumbachi.watchbuddy.model.RecentSearch
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
    suspend fun searchFor(query: String): List<SearchResult>
    suspend fun saveMedia(media: Media)
    suspend fun updateMedia(media: Media)
    suspend fun deleteMedia(media: Media)
    suspend fun findMedia(searchResult: SearchResult): Media?

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

    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting Settings Flow")
        return db.getUserSettingsFlow().distinctUntilChanged()
    }

    override suspend fun getSavedMediaIDs(): Flow<List<WatchbuddyID>> {
        Log.d(TAG, "Getting Saved IDs Flow")
        return db.getSavedMediaIDs().distinctUntilChanged()
    }

    override suspend fun searchFor(query: String): List<SearchResult> {

        val tmdbMovieResults = tmdb.searchMovies(query).toTMDBSearchResults()
        val tmdbShowResults = tmdb.searchShows(query).toTMDBSearchResults()
        val anilistResults = anilist.searchAnime(query).toAnilistAnimeSearchResults()

        return (tmdbMovieResults + tmdbShowResults + anilistResults)
            .sortedByDescending { it.weight() }
            .toList()
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
            Source.TMDBMovie -> tmdb.getMovieDetails(searchResult.id).toTMDBMovie()
            Source.TMDBShow -> tmdb.getShowDetails(searchResult.id).toTMDBShow()
            Source.AnilistMovie -> anilist.getRequiredAnimeDetails(searchResult.id).toAnilistMovie()
            Source.AnilistShow -> anilist.getRequiredAnimeDetails(searchResult.id).toAnilistShow()
            else -> TODO("Add Support for custom items")
        }
    }


    override suspend fun clearSearchHistory() {
        return searchHistory.clear()
    }

    override suspend fun getRecentSearches(count: Int): List<RecentSearch> {
        return searchHistory.take(count)
    }
}