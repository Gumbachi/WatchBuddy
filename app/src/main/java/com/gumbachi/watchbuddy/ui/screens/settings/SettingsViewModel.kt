package com.gumbachi.watchbuddy.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.local.SettingsRepository
import com.gumbachi.watchbuddy.model.enums.CardStyle
import com.gumbachi.watchbuddy.model.enums.ScoreFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val cardStyle: CardStyle = CardStyle.Normal,
    val scoreFormat: ScoreFormat = ScoreFormat.Decimal
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
): ViewModel() {

    var state by mutableStateOf(SettingsUiState())

    fun onCardStyleUpdate(newStyle: CardStyle) {
        viewModelScope.launch {
            repository.updateSettings(
                repository.getUserSettings().copy(cardStyle = newStyle)
            )
            state = state.copy(cardStyle = newStyle)
        }
    }

    fun onScoreFormatUpdate(newFormat: ScoreFormat) {
        viewModelScope.launch {
            repository.updateSettings(
                repository.getUserSettings().copy(scoreFormat = newFormat)
            )
            state = state.copy(scoreFormat = newFormat)
        }
    }
}