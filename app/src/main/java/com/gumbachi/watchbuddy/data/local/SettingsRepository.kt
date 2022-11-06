package com.gumbachi.watchbuddy.data.local

import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.ScoreFormat
import javax.inject.Inject

interface SettingsRepository {
    fun getUserSettings(): UserSettings

    fun updateSettings(newSettings: UserSettings)
}


class SettingsRepositoryImpl @Inject constructor(): SettingsRepository {

    private var settings = UserSettings(
        cardStyle = CardStyle.Normal,
        scoreFormat = ScoreFormat.Decimal
    )

    override fun getUserSettings(): UserSettings {
        return settings
    }

    override fun updateSettings(newSettings: UserSettings) {
        settings = newSettings
    }


}
