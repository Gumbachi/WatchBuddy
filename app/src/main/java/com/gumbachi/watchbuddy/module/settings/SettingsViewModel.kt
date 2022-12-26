package com.gumbachi.watchbuddy.module.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch

private const val TAG = "SettingsViewModel"

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    data class SettingsScreenUiState(
        val loading: Boolean = false,
        val error: Throwable? = null,
        val settings: UserSettings = UserSettings()
    )

    private val _uiState = MutableStateFlow(SettingsScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(loading = true) }
        Log.d(TAG, "Settings ViewModel Created")
        viewModelScope.launch {
            repository.getUserSettingsFlow().withIndex().collectLatest { (index, settings) ->
                Log.d(TAG, "Collected settings state change")
                when (index) {
                    0 -> _uiState.update { it.copy(settings = settings, loading = false) }
                    else -> _uiState.update { it.copy(settings = settings) }
                }
            }
        }
    }

    fun acknowledgeError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun displayError(error: Throwable) {
        _uiState.update { it.copy(error = error) }
    }

    fun updateCardStyleTo(cardStyle: CardStyle) {
        viewModelScope.launch {
            runCatching {
                repository.updateCardStyle(cardStyle)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update card style: $error")
                displayError(error)
            }
        }
    }

    fun updateScoreFormatTo(scoreFormat: ScoreFormat) {
        viewModelScope.launch {
            runCatching {
                repository.updateScoreFormatTo(scoreFormat)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update score format: $error")
                displayError(error)
            }
        }
    }

    fun updateHiddenMovieStatuses(hidden: Set<WatchStatus>) {
        viewModelScope.launch {
            runCatching {
                repository.updateHiddenMovieStatuses(hidden)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update hidden movie statuses: $error")
                displayError(error)
            }
        }
    }

    fun updateHiddenShowStatuses(hidden: Set<WatchStatus>) {
        viewModelScope.launch {
            runCatching {
                repository.updateHiddenShowStatuses(hidden)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update hidden show statuses: $error")
                displayError(error)
            }
        }
    }
}