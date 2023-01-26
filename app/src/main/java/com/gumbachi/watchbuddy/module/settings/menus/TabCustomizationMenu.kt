package com.gumbachi.watchbuddy.module.settings.menus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.module.settings.SettingsViewModel
import com.gumbachi.watchbuddy.ui.components.ColorWrappedColumn
import com.gumbachi.watchbuddy.ui.components.SeparatorSelector
import com.gumbachi.watchbuddy.ui.settings.components.SettingsSwitch

@Composable
fun TabCustomizationMenu(
    state: SettingsViewModel.SettingsScreenUiState,
    updateHiddenMovieStatuses: (Set<WatchStatus>) -> Unit,
    updateHiddenShowStatuses: (Set<WatchStatus>) -> Unit,
    modifier: Modifier = Modifier,
) {

    val hiddenMovieTabs = state.settings.movies.hiddenStatuses
    val hiddenShowTabs = state.settings.shows.hiddenStatuses

    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Movie Tabs", style = MaterialTheme.typography.titleMedium)
        ColorWrappedColumn {
            SeparatorSelector(
                topLabel = "Shown",
                topItems = WatchStatus.values().toList() - hiddenMovieTabs,
                bottomLabel = "Hidden",
                bottomItems = hiddenMovieTabs,
                topIcon = Icons.Filled.Visibility,
                bottomIcon = Icons.Filled.VisibilityOff,
                onChange = { shown, hidden ->
                    if (shown.isNotEmpty()) updateHiddenMovieStatuses(hidden.toSet())
                }
            )
        }

        Text(text = "Shows Tabs", style = MaterialTheme.typography.titleMedium)
        ColorWrappedColumn {
            SeparatorSelector(
                topLabel = "Shown",
                topItems = WatchStatus.values().toList() - hiddenShowTabs,
                bottomLabel = "Hidden",
                bottomItems = hiddenShowTabs,
                topIcon = Icons.Filled.Visibility,
                bottomIcon = Icons.Filled.VisibilityOff,
                onChange = { shown, hidden ->
                    if (shown.isNotEmpty()) updateHiddenShowStatuses(hidden.toSet())
                }
            )
        }


        SettingsSwitch(
            title = "Allow Hidden Status Use",
            description = "Allow media to be saved with hidden statuses",
            icon = Icons.Filled.AllInclusive,
            checked = false,
            onCheckedChange = {}
        )
    }
}