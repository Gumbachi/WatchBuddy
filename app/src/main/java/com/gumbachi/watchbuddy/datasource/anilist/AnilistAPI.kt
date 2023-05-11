package com.gumbachi.watchbuddy.datasource.anilist

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.AnimeEssentialsQuery
import com.gumbachi.watchbuddy.AnimeSearchQuery
import com.gumbachi.watchbuddy.datasource.anilist.mappers.toAnilistAnimeDetails
import com.gumbachi.watchbuddy.datasource.anilist.mappers.toAnilistMovie
import com.gumbachi.watchbuddy.datasource.anilist.mappers.toAnilistSearchResults
import com.gumbachi.watchbuddy.datasource.anilist.mappers.toAnilistShow
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistAnimeDetails
import com.gumbachi.watchbuddy.datasource.anilist.model.AnilistSearchResult

class AnilistAPI {

    private val client = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co")
        .build()

    suspend fun searchMedia(query: String): List<AnilistSearchResult> {
        return client
            .query(AnimeSearchQuery(Optional.present(query)))
            .execute()
            .dataAssertNoErrors
            .toAnilistSearchResults()
    }

    suspend fun getAnimeDetails(id: Int): AnilistAnimeDetails = client
        .query(AnimeDetailsQuery(Optional.present(id)))
        .execute()
        .dataAssertNoErrors
        .toAnilistAnimeDetails()


    suspend fun getBlankShow(id: Int) = client
        .query(AnimeEssentialsQuery(Optional.present(id)))
        .execute()
        .dataAssertNoErrors
        .toAnilistShow()

    suspend fun getBlankMovie(id: Int) = client
        .query(AnimeEssentialsQuery(Optional.present(id)))
        .execute()
        .dataAssertNoErrors
        .toAnilistMovie()
}


