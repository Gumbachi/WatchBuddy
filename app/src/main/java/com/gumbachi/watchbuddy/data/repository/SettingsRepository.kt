package com.gumbachi.watchbuddy.data.repository

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.configuration.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val TAG = "SettingsRepository"

interface SettingsRepository {
    suspend fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun updateSettings(new: UserSettings): Result<Unit>
    suspend fun updateScoreFormat(new: ScoreFormat): Result<Unit>
    suspend fun updateCardStyle(new: CardStyle): Result<Unit>
    suspend fun updateMovieSort(new: Sort): Result<Unit>
    suspend fun updateShowSort(new: Sort): Result<Unit>
}

class SettingsRepositoryImpl @Inject constructor(
    private val db: WatchbuddyDatabase
): SettingsRepository {

    init {
        Log.d(TAG, "Settings Repository Created")
    }

    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {
        Log.d(TAG, "Getting User Settings Flow")
        return db.getUserSettingsFlow()
    }

    override suspend fun updateSettings(new: UserSettings): Result<Unit> {
        return runCatching {
            Log.d(TAG, "Updating all settings")
            db.updateUserSettings(new)
        }
    }

    override suspend fun updateScoreFormat(new: ScoreFormat): Result<Unit> {
        return runCatching {
            val current = db.getUserSettingsFlow().first()
            db.updateUserSettings(current.copy(scoreFormat = new))
        }
    }

    override suspend fun updateCardStyle(new: CardStyle): Result<Unit> {
        return runCatching {
            val current = db.getUserSettingsFlow().first()
            db.updateUserSettings(current.copy(cardStyle = new))
        }
    }

    override suspend fun updateMovieSort(new: Sort): Result<Unit> {
        return runCatching {
            Log.d(TAG, "Updating Movie Sort")
            val current = db.getUserSettingsFlow().first()
            db.updateUserSettings(current.copy(movieSort = new))
        }
    }

    override suspend fun updateShowSort(new: Sort): Result<Unit> {
        return runCatching {
            val current = db.getUserSettingsFlow().first()
            db.updateUserSettings(current.copy(showSort = new))
        }
    }

}
