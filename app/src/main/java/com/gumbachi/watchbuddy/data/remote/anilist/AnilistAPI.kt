package com.gumbachi.watchbuddy.data.remote.anilist

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.AnimeSearchQuery
import com.gumbachi.watchbuddy.BlankAnimeQuery
import com.gumbachi.watchbuddy.data.remote.anilist.mappers.toAnilistMovieDetails
import com.gumbachi.watchbuddy.data.remote.anilist.mappers.toAnilistSearchResults
import com.gumbachi.watchbuddy.data.remote.anilist.mappers.toAnilistShowDetails
import com.gumbachi.watchbuddy.model.api.anilist.AnilistMovieDetails
import com.gumbachi.watchbuddy.model.api.anilist.AnilistSearchResult
import com.gumbachi.watchbuddy.model.api.anilist.AnilistShowDetails

interface AnilistAPI {
    suspend fun searchAnime(query: String): List<AnilistSearchResult>
    suspend fun getAnilistShowDetails(id: Int): AnilistShowDetails
    suspend fun getAnilistMovieDetails(id: Int): AnilistMovieDetails
    suspend fun getRequiredAnimeDetails(id: Int): BlankAnimeQuery.Data // Only data required to save
}

class AnilistAPIImpl : AnilistAPI {

    private val client = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co")
        .build()

    override suspend fun searchAnime(query: String): List<AnilistSearchResult> {
        return client
            .query(AnimeSearchQuery(Optional.present(query)))
            .execute()
            .dataAssertNoErrors
            .toAnilistSearchResults()
    }

    override suspend fun getAnilistShowDetails(id: Int): AnilistShowDetails {
        return client
            .query(AnimeDetailsQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
            .toAnilistShowDetails()
    }

    override suspend fun getAnilistMovieDetails(id: Int): AnilistMovieDetails {
        return client
            .query(AnimeDetailsQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
            .toAnilistMovieDetails()
    }

    override suspend fun getRequiredAnimeDetails(id: Int): BlankAnimeQuery.Data {
        return client
            .query(BlankAnimeQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
    }


}


