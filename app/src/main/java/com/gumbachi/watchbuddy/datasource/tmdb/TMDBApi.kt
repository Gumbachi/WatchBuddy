package com.gumbachi.watchbuddy.datasource.tmdb

import com.gumbachi.watchbuddy.BuildConfig
import com.gumbachi.watchbuddy.datasource.WatchBuddyDataSource
import com.gumbachi.watchbuddy.datasource.tmdb.dto.*
import com.gumbachi.watchbuddy.datasource.tmdb.mappers.*
import com.gumbachi.watchbuddy.datasource.tmdb.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class TMDBApi : WatchBuddyDataSource {

    private val baseURL = "https://api.themoviedb.org/3"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("https://api.themoviedb.org/3/")
        }
        expectSuccess = true
    }
    private val tmdbKey = BuildConfig.TMDB_KEY

    suspend fun searchMovies(query: String): List<TMDBMovieSearchResult> = client.get {
        url("$baseURL/search/movie?language=en-US")
        parameter("query", query)
        parameter("api_key", tmdbKey)
    }
        .body<TMDBMovieSearchResponseDTO>()
        .toTMDBSearchResults()

    suspend fun getBlankMovie(id: Int): TMDBMovie = client.get {
        url("$baseURL/movie/$id?language=en-US")
        parameter("api_key", tmdbKey)
    }.body<TMDBMovieEssentialsDTO>().toTMDBMovie()


    suspend fun getBlankShow(id: Int): TMDBShow = client.get {
        url("$baseURL/movie/$id?language=en-US")
        parameter("api_key", tmdbKey)
    }
        .body<TMDBShowEssentialsDTO>()
        .toTMDBShow()

    override suspend fun getMovieDetails(id: Int): TMDBMovieDetails = client.get {
        url("$baseURL/movie/$id?language=en-US")
        parameter("api_key", tmdbKey)
        parameter("append_to_response", "credits,images,recommendations,reviews")
    }
        .body<TMDBMovieDetailsDTO>()
        .toTMDBMovieDetails()


    suspend fun searchShows(query: String): List<TMDBShowSearchResult> = client.get {
        url("$baseURL/search/tv?language=en-US")
        parameter("query", query)
        parameter("api_key", tmdbKey)
    }
        .body<TMDBShowSearchResponseDTO>()
        .toTMDBSearchResults()


    override suspend fun searchMedia(query: String) = searchMovies(query) + searchShows(query)

    override suspend fun getShowDetails(id: Int): TMDBShowDetails = client.get {
        url("$baseURL/tv/$id?language=en-US")
        parameter("api_key", tmdbKey)
        parameter("append_to_response", "credits,images,recommendations,reviews")
    }
        .body<TMDBShowDetailsDTO>()
        .toTMDBShowDetails()
}
