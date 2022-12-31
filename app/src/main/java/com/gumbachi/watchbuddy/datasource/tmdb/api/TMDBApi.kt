package com.gumbachi.watchbuddy.datasource.tmdb.api

import com.gumbachi.watchbuddy.BuildConfig
import com.gumbachi.watchbuddy.datasource.WatchBuddyDataSource
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.movie.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.movie.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show.TMDBShowDetailsDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.dto.show.TMDBShowSearchResponseDTO
import com.gumbachi.watchbuddy.datasource.tmdb.api.mappers.toTMDBMovieDetails
import com.gumbachi.watchbuddy.datasource.tmdb.api.mappers.toTMDBSearchResults
import com.gumbachi.watchbuddy.datasource.tmdb.api.mappers.toTMDBShowDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBMovieSearchResult
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowDetails
import com.gumbachi.watchbuddy.datasource.tmdb.model.TMDBShowSearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.json.json
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

    suspend fun searchMovies(query: String): List<TMDBMovieSearchResult> {
        val searchResponseDTO: TMDBMovieSearchResponseDTO =  client.get {
            url("$baseURL/search/movie?language=en-US")
            parameter("query", query)
            parameter("api_key", tmdbKey)
        }.body()
        return searchResponseDTO.toTMDBSearchResults()
    }

    override suspend fun getMovieDetails(id: Int): TMDBMovieDetails {
        val detailsDTO: TMDBMovieDetailsDTO = client.get {
            url("$baseURL/movie/$id?language=en-US")
            parameter("api_key", tmdbKey)
        }.body()
        return detailsDTO.toTMDBMovieDetails()
    }

    suspend fun searchShows(query: String): List<TMDBShowSearchResult> {
        val searchResponseDTO: TMDBShowSearchResponseDTO = client.get {
            url("$baseURL/search/tv?language=en-US")
            parameter("query", query)
            parameter("api_key", tmdbKey)
        }.body()
        return searchResponseDTO.toTMDBSearchResults()
    }

    override suspend fun searchMedia(query: String) = searchMovies(query) + searchShows(query)

    override suspend fun getShowDetails(id: Int): TMDBShowDetails {
        val detailsDTO: TMDBShowDetailsDTO = client.get {
            url("$baseURL/tv/$id?language=en-US")
            parameter("api_key", tmdbKey)
        }.body()
        return detailsDTO.toTMDBShowDetails()
    }
}
