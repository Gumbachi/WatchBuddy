package com.gumbachi.watchbuddy.data.remote.tmdb

import com.gumbachi.watchbuddy.BuildConfig
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowSearchResponseDTO
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

// TODO This could be cleaned up
object Endpoints {
    private const val BASE_URL = "https://api.themoviedb.org/3"
    const val SEARCH_MOVIES = "$BASE_URL/search/movie?language=en-US"
    fun movieDetails(id: Int) = "$BASE_URL/movie/$id?language=en-US"

    const val SEARCH_SHOWS = "$BASE_URL/search/tv?language=en-US"
    fun showDetails(id: Int) = "$BASE_URL/tv/$id?language=en-US"
}

interface TMDBApi {
    suspend fun searchMovies(query: String): TMDBMovieSearchResponseDTO
    suspend fun getMovieDetails(id: Int): TMDBMovieDetailsDTO

    suspend fun searchShows(query: String): TMDBShowSearchResponseDTO
    suspend fun getShowDetails(id: Int): TMDBShowDetailsDTO
}

class TMDBApiImpl : TMDBApi {

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

    override suspend fun searchMovies(query: String): TMDBMovieSearchResponseDTO {
        return client.get {
            url(Endpoints.SEARCH_MOVIES)
            parameter("query", query)
            parameter("api_key", tmdbKey)
        }.body()
    }

    override suspend fun getMovieDetails(id: Int): TMDBMovieDetailsDTO {
        return client.get {
            url(Endpoints.movieDetails(id))
            parameter("api_key", tmdbKey)
        }.body()
    }

    override suspend fun searchShows(query: String): TMDBShowSearchResponseDTO {
        return client.get {
            url(Endpoints.SEARCH_SHOWS)
            parameter("query", query)
            parameter("api_key", tmdbKey)
        }.body()
    }

    override suspend fun getShowDetails(id: Int): TMDBShowDetailsDTO {
        return client.get {
            url(Endpoints.showDetails(id))
            parameter("api_key", tmdbKey)
        }.body()
    }
}
