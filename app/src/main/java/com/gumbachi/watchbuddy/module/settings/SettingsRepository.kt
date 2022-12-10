package com.gumbachi.watchbuddy.module.settings

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first

private const val TAG = "SettingsRepository"

interface SettingsRepository {
    suspend fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun updateSettings(new: UserSettings)
    suspend fun updateScoreFormatTo(new: ScoreFormat)
    suspend fun updateCardStyle(new: CardStyle)
    suspend fun updateMovieSort(new: Sort)
    suspend fun updateShowSort(new: Sort)
    suspend fun updateHiddenMovieStatuses(hidden: Set<WatchStatus>)
    suspend fun updateHiddenShowStatuses(hidden: Set<WatchStatus>)
}

class SettingsRepositoryImpl(
    private val db: WatchbuddyDatabase
) : SettingsRepository {

    init {
        Log.d(TAG, "Settings Repository Created")
    }

    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting User Settings Flow")
        return db.getUserSettingsFlow().distinctUntilChanged()
    }

    override suspend fun updateSettings(new: UserSettings) {
        Log.d(TAG, "Updating all settings")
        db.updateUserSettingsTo(new)
    }

    override suspend fun updateScoreFormatTo(new: ScoreFormat) {
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(scoreFormat = new))
    }

    override suspend fun updateCardStyle(new: CardStyle) {
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(cardStyle = new))
    }

    override suspend fun updateMovieSort(new: Sort) {
        Log.d(TAG, "Updating Movie Sort")
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(movieSort = new))
    }

    override suspend fun updateShowSort(new: Sort) {
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(showSort = new))
    }

    override suspend fun updateHiddenMovieStatuses(hidden: Set<WatchStatus>) {
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(hiddenMovieStatuses = hidden))
    }

    override suspend fun updateHiddenShowStatuses(hidden: Set<WatchStatus>) {
        val current = db.getUserSettingsFlow().first()
        db.updateUserSettingsTo(current.copy(hiddenShowStatuses = hidden))
    }
}
