package com.gumbachi.watchbuddy.ui.screens.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.local.DetailsRepository
import com.gumbachi.watchbuddy.model.enums.MediaType
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.model.interfaces.Details
import com.gumbachi.watchbuddy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val loading: Boolean = false,
    val details: Details? = null
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
): ViewModel() {

    init {
        Log.d("VM", "Details VM Created")
    }

    var state by mutableStateOf(DetailsUiState())

    fun loadDetails(api: SourceAPI, type: MediaType, id: Int) {
        viewModelScope.launch {
            state = state.copy(loading = true)

            val details = when (Pair(api, type)) {
                Pair(SourceAPI.TMDB, MediaType.Movie) -> {
                    repository.getTMDBMovieDetails(id)
                }
                Pair(SourceAPI.TMDB, MediaType.Show) -> {
                    repository.getTMDBShowDetails(id)
                }
                else -> null
            }

            when (details) {
                is Resource.Success -> {
                    state = state.copy(loading = false, details = details.data)
                    Log.d("Details","Successful details fetch: ${details.data}")
                }
                is Resource.Error -> {
                    state = state.copy(loading = false, details = null)
                    Log.w("Details","Error with API Fetching $api, $type, $id")
                }
                null -> {
                    state = state.copy(loading = false, details = null)
                    Log.e("Details","Invalid API/MediaType Input")
                }
            }
        }
    }
}