package com.gumbachi.watchbuddy.data.local

import android.util.Log
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.utils.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject

interface ShowRepository {
    suspend fun getShows(): Resource<List<Show>>
}

class ShowRepositoryImpl @Inject constructor(): ShowRepository {

    private val shows = emptyList<Show>()

    init {
        Log.d("Repository", "Show Repository Created")
    }

    override suspend fun getShows(): Resource<List<Show>> {
        delay(2000L)
        return try {
            Resource.Success(shows)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown Error Occurred")
        }

    }

}