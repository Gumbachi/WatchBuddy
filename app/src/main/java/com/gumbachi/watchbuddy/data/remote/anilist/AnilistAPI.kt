package com.gumbachi.watchbuddy.data.remote.anilist

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.gumbachi.watchbuddy.AnimeDetailsQuery
import com.gumbachi.watchbuddy.AnimeSearchQuery
import com.gumbachi.watchbuddy.BlankAnimeQuery

interface AnilistAPI {
    suspend fun searchAnime(query: String): AnimeSearchQuery.Data
    suspend fun getAnimeDetails(id: Int): AnimeDetailsQuery.Data // All data
    suspend fun getRequiredAnimeDetails(id: Int): BlankAnimeQuery.Data // Only date required to save
}

class AnilistAPIImpl : AnilistAPI {

    private val client = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co")
        .build()

    override suspend fun searchAnime(query: String): AnimeSearchQuery.Data {
        return client
            .query(AnimeSearchQuery(Optional.present(query)))
            .execute()
            .dataAssertNoErrors
    }

    override suspend fun getAnimeDetails(id: Int): AnimeDetailsQuery.Data {
        return client
            .query(AnimeDetailsQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
    }

    override suspend fun getRequiredAnimeDetails(id: Int): BlankAnimeQuery.Data {
        return client
            .query(BlankAnimeQuery(Optional.present(id)))
            .execute()
            .dataAssertNoErrors
    }


}


