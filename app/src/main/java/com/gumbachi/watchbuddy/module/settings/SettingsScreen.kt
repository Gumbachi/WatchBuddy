package com.gumbachi.watchbuddy.module.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.screens.settings.SettingsScreenOption
import com.gumbachi.watchbuddy.ui.screens.settings.SettingsScreenSections
import com.gumbachi.watchbuddy.ui.screens.settings.components.CardCustomizer
import com.gumbachi.watchbuddy.ui.screens.settings.components.WatchStatusTabCustomizer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Settings") })
        }
    ) {
        SettingsScreenSections {
            SettingsScreenOption(
                title = "Card Customization",
                description = "Customize card style, score format, and more",
                icon = Icons.Filled.FormatSize
            ) {
                CardCustomizer(
                    cardStyle = state.settings.cardStyle,
                    onCardStyleChange = { viewModel.updateCardStyleTo(it) },
                    scoreFormat = state.settings.scoreFormat,
                    onScoreFormatChange = { viewModel.updateScoreFormatTo(it) }
                )
            }
            SettingsScreenOption(
                title = "Movies Tabs Customization",
                description = "Customize which tabs show for movies",
                icon = Icons.Filled.MovieFilter
            ) {
                WatchStatusTabCustomizer(
                    hiddenCategories = state.settings.hiddenMovieStatuses,
                    onHiddenCategoriesChange = { viewModel.updateHiddenMovieStatuses(it.toSet()) }
                )
            }
            SettingsScreenOption(
                title = "Shows Tabs Customization",
                description = "Customize which tabs show for shows",
                icon = Icons.Filled.Tv
            ) {
                WatchStatusTabCustomizer(
                    hiddenCategories = state.settings.hiddenMovieStatuses,
                    onHiddenCategoriesChange = { viewModel.updateHiddenMovieStatuses(it.toSet()) }
                )
            }
            SettingsScreenOption(
                title = "TMDB Settings",
                description = "Modify settings for TMDB Specific Media",
                icon = Icons.Filled.Settings
            ) {
                Text("In Progress") // TODO Fill in about section
            }
            SettingsScreenOption(
                title = "Anilist Settings",
                description = "Modify settings for Anilist Specific Media",
                icon = Icons.Filled.Settings
            ) {
                Text("In Progress") // TODO Fill in section
            }
            SettingsScreenOption(
                title = "About",
                description = "Extra information about WatchBuddy",
                icon = Icons.Filled.QuestionMark
            ) {
                Text("In Progress") // TODO Fill in about section
            }
        }
    }
}

