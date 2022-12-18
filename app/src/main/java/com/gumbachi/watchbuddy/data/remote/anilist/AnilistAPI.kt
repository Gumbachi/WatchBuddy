package com.gumbachi.watchbuddy.data.remote.anilist

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.gumbachi.watchbuddy.AnimeSearchResultsDTOQuery

interface AnilistAPI {
    suspend fun searchAnime(query: String): AnimeSearchResultsDTOQuery.Data
}

class AnilistAPIImpl : AnilistAPI {

    private val client = ApolloClient.Builder()
        .serverUrl("https://graphql.anilist.co")
        .build()

    override suspend fun searchAnime(query: String): AnimeSearchResultsDTOQuery.Data {
        return client.query(
            AnimeSearchResultsDTOQuery(Optional.present(query))
        ).execute().dataAssertNoErrors
    }

}


