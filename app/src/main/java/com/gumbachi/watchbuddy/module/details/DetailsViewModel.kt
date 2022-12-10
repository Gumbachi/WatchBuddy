package com.gumbachi.watchbuddy.module.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.WatchbuddyID
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.Detailable
import kotlinx.coroutines.launch

data class DetailsUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val details: Detailable? = null
)

class DetailsViewModel(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    init {
        Log.d("VM", "Details VM Created")
    }

    var state by mutableStateOf(DetailsUiState())

    fun loadDetails(id: WatchbuddyID) {
        viewModelScope.launch {
            state = state.copy(loading = true)

            val details = when (id.source) {
                Source.TMDBMovie -> detailsRepository.getTMDBMovieDetails(id.sourceID)
                Source.TMDBShow -> detailsRepository.getTMDBShowDetails(id.sourceID)
                else -> TODO("Need to fill this in for other APIs")
            }.onSuccess {
                state = state.copy(loading = false, details = it, error = null)
            }.onFailure {
                state = state.copy(loading = false, details = null, error = it)
                Log.e("Details", "Failed to load details: $it")
            }
        }
    }
}