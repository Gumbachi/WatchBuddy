package com.gumbachi.watchbuddy.datasource.anilist.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.AnimeSearchQuery
import com.gumbachi.watchbuddy.BlankAnimeQuery
import com.gumbachi.watchbuddy.datasource.WatchBuddyDataSource
import com.gumbachi.watchbuddy.datasource.anilist.api.mappers.toAnilistMovieDetails
import com.gumbachi.watchbuddy.datasource.anilist.api.mappers.toAnilistSearchResults
import com.gumbachi.watchbuddy.datasource.anilist.api.mappers.toAnilistShowDetails
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistMovieDetails
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistSearchResult
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistShowDetails

class AnilistAPI : WatchBuddyDataSource {

    private val client = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co")
        .build()

    override suspend fun searchMedia(query: String): List<AnilistSearchResult> {
        return client
            .query(AnimeSearchQuery(Optional.present(query)))
            .execute()
            .dataAssertNoErrors
            .toAnilistSearchResults()
    }

    override suspend fun getShowDetails(id: Int): AnilistShowDetails {
        return client
            .query(AnimeDetailsQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
            .toAnilistShowDetails()
    }

    override suspend fun getMovieDetails(id: Int): AnilistMovieDetails {
        return client
            .query(AnimeDetailsQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
            .toAnilistMovieDetails()
    }

    suspend fun getRequiredAnimeDetails(id: Int): BlankAnimeQuery.Data {
        return client
            .query(BlankAnimeQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
    }


}


