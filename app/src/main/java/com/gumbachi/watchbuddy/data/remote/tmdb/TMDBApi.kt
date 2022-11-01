package com.gumbachi.watchbuddy.data.remote.tmdb

import com.gumbachi.watchbuddy.data.remote.tmdb.models.movie.search.TMDBMovieSearchResponse
import com.gumbachi.watchbuddy.BuildConfig
import com.gumbachi.watchbuddy.data.remote.tmdb.models.movie.details.TMDBMovieDetails
import com.gumbachi.watchbuddy.data.remote.tmdb.models.show.details.TMDBShowDetails
import com.gumbachi.watchbuddy.data.remote.tmdb.models.show.search.TMDBShowSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("/search/movie?language=en-US")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBMovieSearchResponse

    @GET("/search/tv?language=en-US")
    suspend fun searchShows(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBShowSearchResponse

    @GET("/tv/{id}?language=en-US")
    suspend fun getShowDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBShowDetails

    @GET("/movie/{id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBMovieDetails
}