package com.gumbachi.watchbuddy.data.repository

import android.util.Log
import com.gumbachi.watchbuddy.model.interfaces.Show
import javax.inject.Inject

interface ShowRepository {
    suspend fun getShows(): Result<List<Show>>
}

class ShowRepositoryImpl @Inject constructor(): ShowRepository {

    private val _shows = emptyList<Show>()

    init {
        Log.d("Repository", "Show Repository Created")
    }

    override suspend fun getShows(): Result<List<Show>> {
        return runCatching {
            _shows
        }
    }
}