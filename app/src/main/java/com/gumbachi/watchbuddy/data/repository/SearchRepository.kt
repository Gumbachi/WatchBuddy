package com.gumbachi.watchbuddy.data.repository

import com.gumbachi.watchbuddy.data.remote.tmdb.TMDBApi
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBMovie
import com.gumbachi.watchbuddy.data.remote.tmdb.mappers.toTMDBSearchResults
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.model.SearchFilter
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.interfaces.SearchResult
import com.gumbachi.watchbuddy.model.tmdb.TMDBMovieSearchResult
import com.gumbachi.watchbuddy.model.tmdb.TMDBShowSearchResult
import java.time.LocalDateTime
import javax.inject.Inject

interface SearchRepository {
    suspend fun searchFiltered(query: String, filter: SearchFilter): Result<List<SearchResult>>
    suspend fun searchTMDBMovies(query: String): Result<List<TMDBMovieSearchResult>>
    suspend fun searchTMDBShows(query: String): Result<List<TMDBShowSearchResult>>
    suspend fun generateBlankMedia(searchResult: SearchResult): Result<Media>
    suspend fun getRecentSearches(count: Int): Result<List<RecentSearch>>
    suspend fun clearSearchHistory(): Result<Unit>
}


class SearchRepositoryImpl @Inject constructor(
    private val tmdb: TMDBApi
): SearchRepository {

    private var searchHistory = ArrayDeque<RecentSearch>()

    override suspend fun searchTMDBMovies(query: String): Result<List<TMDBMovieSearchResult>> {
        return runCatching {
            val response = tmdb.searchMovies(query)

            searchHistory.add(
                RecentSearch(
                    query = query,
                    resultCount = response.total_results,
                    searchTime = LocalDateTime.now()
                )
            )

            response.toTMDBSearchResults()
        }
    }

    override suspend fun searchTMDBShows(query: String): Result<List<TMDBShowSearchResult>> {
        return runCatching {
            tmdb.searchShows(query).toTMDBSearchResults()
        }
    }

    override suspend fun searchFiltered(
        query: String,
        filter: SearchFilter
    ): Result<List<SearchResult>> {
        return runCatching {
            val results = mutableListOf<SearchResult>()

            if (filter.tmdbMovies) {
                results.addAll(tmdb.searchMovies(query).toTMDBSearchResults())
            }

            if (filter.tmdbShows) {
                results.addAll(tmdb.searchShows(query).toTMDBSearchResults())
            }

            results.sortedByDescending { it.weight() }
        }
    }

    override suspend fun generateBlankMedia(searchResult: SearchResult): Result<Media> {
        return runCatching {
            when (searchResult.source) {
                Source.TMDBMovie -> tmdb.getMovieDetails(id = searchResult.id).toTMDBMovie()
                else -> TODO("Fill remaining branches")
            }
        }
    }

    override suspend fun clearSearchHistory(): Result<Unit> {
        return runCatching {
            searchHistory.clear()
        }
    }

    override suspend fun getRecentSearches(count: Int): Result<List<RecentSearch>> {
        return runCatching {
            searchHistory.take(count)
        }
    }
}