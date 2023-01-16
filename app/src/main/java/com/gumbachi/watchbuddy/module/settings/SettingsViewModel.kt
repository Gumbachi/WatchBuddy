package com.gumbachi.watchbuddy.module.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.settings.*
import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import kotlinx.coroutines.flow.*
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

    //region General Settings
    private fun updateGeneralSettings(settings: GeneralSettings) {
        viewModelScope.launch {
            runCatching {
                val newSettings = uiState.value.settings.copy(general = settings)
                repository.updateSettingsTo(newSettings)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update General Settings: $error")
                displayError(error)
            }
        }
    }

    fun updateStartingDestination(destination: WatchbuddyMainDestination) {
        updateGeneralSettings(uiState.value.settings.general.copy(startingDestination = destination))
    }
    //endregion


    //region Card Settings
    private fun updateCardSettings(settings: CardSettings) {
        viewModelScope.launch {
            runCatching {
                val newSettings = uiState.value.settings.copy(card = settings)
                repository.updateSettingsTo(newSettings)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update Card Settings: $error")
                displayError(error)
            }
        }
    }

    fun updateCardStyle(style: CardStyle) {
        updateCardSettings(uiState.value.settings.card.copy(style = style))
    }

    fun updateScoreFormat(format: ScoreFormat) {
        updateCardSettings(uiState.value.settings.card.copy(scoreFormat = format))
    }

    fun updateShowProgressOnPlanned(showProgressOnPlanned: Boolean) {
        updateCardSettings(uiState.value.settings.card.copy(showProgressOnPlanned = showProgressOnPlanned))
    }

    fun updateShowScoreOnPlanned(showScoreOnPlanned: Boolean) {
        updateCardSettings(uiState.value.settings.card.copy(showScoreOnPlanned = showScoreOnPlanned))
    }

    fun updateShowApiIndicator(showApi: Boolean) {
        updateCardSettings(uiState.value.settings.card.copy(showApi = showApi))
    }
    //endregion


    //region TMDB Settings
    private fun updateTMDBSettings(settings: TMDBSettings) {
        viewModelScope.launch {
            runCatching {
                val newSettings = uiState.value.settings.copy(tmdb = settings)
                repository.updateSettingsTo(newSettings)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update TMDB Settings: $error")
                displayError(error)
            }
        }
    }

    fun updateTMDBEnabled(enabled: Boolean) {
        updateTMDBSettings(uiState.value.settings.tmdb.copy(enabled = enabled))
    }

    fun updateTMDBIncludeAdult(enabled: Boolean) {
        updateTMDBSettings(uiState.value.settings.tmdb.copy(adult = enabled))
    }
    //endregion


    //region Anilist Settings
    private fun updateAnilistSettings(settings: AnilistSettings) {
        viewModelScope.launch {
            runCatching {
                val newSettings = uiState.value.settings.copy(anilist = settings)
                repository.updateSettingsTo(newSettings)
            }.onFailure { error ->
                Log.e(TAG, "Couldn't update Anilist Settings: $error")
                displayError(error)
            }
        }
    }

    fun updateAnilistEnabled(enabled: Boolean) {
        updateAnilistSettings(uiState.value.settings.anilist.copy(enabled = enabled))
    }

    fun updateAnilistIncludeAdult(adult: Boolean) {
        updateAnilistSettings(uiState.value.settings.anilist.copy(adult = adult))
    }

    fun updateAnilistPreferredLanguage(language: AnilistTitleLanguage) {
        updateAnilistSettings(uiState.value.settings.anilist.copy(preferredLanguage = language))
    }
    //endregion
}