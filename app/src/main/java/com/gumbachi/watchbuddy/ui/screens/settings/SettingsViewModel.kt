package com.gumbachi.watchbuddy.ui.screens.settings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.repository.SettingsRepository
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SettingsViewModel"

data class SettingsUiState(
    val loading: Boolean = true,
    val error: Throwable? = null,
    val settings: UserSettings = UserSettings()
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    init {
        Log.d(TAG, "Settings ViewModel Created")
        viewModelScope.launch {
            repository.getUserSettingsFlow().collect {
                Log.d(TAG,"Collected a state change $it")
                state = state.copy(settings = it, loading = false)
            }
        }
    }

    var state by mutableStateOf(SettingsUiState())
        private set

    fun onCardStyleUpdate(newStyle: CardStyle) {
        viewModelScope.launch {
            repository.updateCardStyle(newStyle)
                .onFailure {
                    state = state.copy(error = it)
                    Log.e(TAG, "Couldn't update card style")
                }
        }
    }

    fun onScoreFormatUpdate(newFormat: ScoreFormat) {
        viewModelScope.launch {
            repository.updateScoreFormat(newFormat)
                .onFailure {
                    Log.e(TAG, "Couldn't update score format")
                    state = state.copy(error = it)
                }
        }
    }

//    fun syncSettings() {
//        viewModelScope.launch {
//            state = state.copy(loading = true)
//            repository.getUserSettings()
//                .onSuccess { state = state.copy(loading = false, settings = it) }
//                .onFailure {
//                    Log.e(TAG, "Couldn't retrieve user settings")
//                    state = state.copy(loading = false, error = it)
//                }
//        }
//    }
}