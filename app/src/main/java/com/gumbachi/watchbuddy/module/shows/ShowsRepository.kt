package com.gumbachi.watchbuddy.module.shows

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.Watchlist
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.interfaces.Show
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val TAG = "ShowRepository"

interface ShowsRepository {

    suspend fun getSettingsFlow(): Flow<UserSettings>
    suspend fun getWatchListFlow(): Flow<Watchlist<Show>>
    suspend fun addShow(show: Show)
    suspend fun updateShowTo(updatedShow: Show)
    suspend fun removeShow(show: Show)
    suspend fun updateShowSortTo(sort: Sort)

}

class ShowsRepositoryImpl(
    private val db: WatchbuddyDatabase
) : ShowsRepository {

    init {
        Log.d(TAG, "Show Repository Created")
    }

    override suspend fun getSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting Settings Flow")
        return db.getUserSettingsFlow().distinctUntilChanged()
    }

    override suspend fun getWatchListFlow(): Flow<Watchlist<Show>> {
        Log.d(TAG, "Fetching Watchlist...")
        val currentSort = db.getUserSettingsFlow().first().showSort
        return db.getShowsFlow().distinctUntilChanged().map {
            Watchlist(entries = it, sort = currentSort)
        }
    }

    override suspend fun addShow(show: Show) {
        Log.d(TAG, "Saving Show...")
        db.addShow(show)
    }

    override suspend fun updateShowTo(updatedShow: Show) {
        Log.d(TAG, "Updating ${updatedShow.watchbuddyID} to $updatedShow")
        db.updateShow(updatedShow)
    }

    override suspend fun removeShow(show: Show) {
        Log.d(TAG, "Removing $show")
        db.removeShow(show)
    }

    override suspend fun updateShowSortTo(sort: Sort) {
        Log.d(TAG, "Updating show sort")
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(showSort = sort))
    }
}