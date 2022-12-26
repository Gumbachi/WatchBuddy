package com.gumbachi.watchbuddy.module.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.Detailable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "DetailsViewModel"

class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {

    data class DetailsScreenUiState(
        val loading: Boolean = false,
        val error: Throwable? = null,

        val details: Detailable? = null
    )

    init {
        Log.d("VM", "Details VM Created")
    }

    private val _uiState = MutableStateFlow(DetailsScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun loadDetailsFor(id: WatchbuddyID) {
        _uiState.update { it.copy(loading = true) }
        viewModelScope.launch {
            runCatching {
                when (id.source) {
                    Source.TMDBMovie -> repository.getTMDBMovieDetails(id.sourceID)
                    Source.TMDBShow -> repository.getTMDBShowDetails(id.sourceID)
                    Source.AnilistMovie -> repository.getAnilistMovieDetails(id.sourceID)
                    Source.AnilistShow -> repository.getAnilistShowDetails(id.sourceID)
                    else -> TODO("Need to fill this in for other APIs")
                }
            }.onSuccess { details ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        details = details,
                        error = null
                    )
                }
            }.onFailure { error ->
                Log.e(TAG, "Failed to load details: $error")
                _uiState.update { it.copy(loading = false, error = error) }
            }
        }
    }
}