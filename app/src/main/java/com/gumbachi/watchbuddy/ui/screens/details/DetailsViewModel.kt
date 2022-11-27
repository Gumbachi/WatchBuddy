package com.gumbachi.watchbuddy.ui.screens.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.data.repository.DetailsRepository
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.interfaces.Details
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val details: Details? = null
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
): ViewModel() {

    init {
        Log.d("VM", "Details VM Created")
    }

    var state by mutableStateOf(DetailsUiState())

    fun loadDetails(source: Source, id: Int) {
        viewModelScope.launch {
            state = state.copy(loading = true)

            val details = when (source) {
                Source.TMDBMovie -> detailsRepository.getTMDBMovieDetails(id)
                Source.TMDBShow -> detailsRepository.getTMDBShowDetails(id)
                else -> TODO("Need to fill this in for other APIs")
            }.onSuccess {
                state = state.copy(loading = false, details = it, error = null)
            }.onFailure {
                state = state.copy(loading = false, details = null, error = it)
                Log.e("Details","Failed to load details: $it")
            }
        }
    }
}