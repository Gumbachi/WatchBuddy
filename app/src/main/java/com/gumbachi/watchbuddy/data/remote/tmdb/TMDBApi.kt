package com.gumbachi.watchbuddy.data.remote.tmdb

import com.gumbachi.watchbuddy.BuildConfig
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.movie.TMDBMovieSearchResponseDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowDetailsDTO
import com.gumbachi.watchbuddy.data.remote.tmdb.dto.show.TMDBShowSearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("search/movie?language=en-US")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBMovieSearchResponseDTO

    @GET("movie/{id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBMovieDetailsDTO

    @GET("search/tv?language=en-US")
    suspend fun searchShows(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBShowSearchResponseDTO

    @GET("tv/{id}?language=en-US")
    suspend fun getShowDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_KEY,
    ) : TMDBShowDetailsDTO


}