package com.gumbachi.watchbuddy.module.search.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumbachi.watchbuddy.model.RecentSearch
import com.gumbachi.watchbuddy.model.settings.UserSettings
import com.gumbachi.watchbuddy.model.interfaces.Cardable
import com.gumbachi.watchbuddy.module.search.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SearchViewModel"

data class SearchHomeUiState(
    val loading: Boolean = false,
    val error: Throwable? = null,


    val recentSearches: List<RecentSearch> = emptyList(),
    val settings: UserSettings = UserSettings(),

    val recentItems: List<Cardable> = emptyList()

)

class SearchHomeViewModel(private val repository: SearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchHomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "Search View Model Created")
        viewModelScope.launch {
            launch {
                repository.getUserSettingsFlow().collectLatest { settings ->
                    _uiState.update { it.copy(settings = settings) }
                }
            }
            launch {
                repository.getRecentsFlow().collectLatest { recents ->
                    _uiState.update { it.copy(recentItems = recents) }
                }
            }
        }
    }



}
