package com.gumbachi.watchbuddy.module.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoSizeSelectActual
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tab
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.R
import com.gumbachi.watchbuddy.module.settings.menus.*
import com.gumbachi.watchbuddy.ui.components.WatchbuddyScaffold
import com.gumbachi.watchbuddy.ui.settings.components.SettingsScreenItem
import com.gumbachi.watchbuddy.ui.settings.components.SettingsScreenSection
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    val generalSettings = state.settings.general
    val cardSettings = state.settings.card
    val tmdbSettings = state.settings.tmdb
    val anilistSettings = state.settings.anilist

    WatchbuddyScaffold(
        isLoading = state.loading,
        error = state.error,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SettingsScreenSection(title = "App Style") {
                SettingsScreenItem(
                    title = "General Settings",
                    icon = rememberVectorPainter(image = Icons.Filled.Settings)
                ) {
                    GeneralSettingMenu(
                        settings = generalSettings,
                        updateStartingDestination = viewModel::updateStartingDestination
                    )
                }

                SettingsScreenItem(
                    title = "Card Customization",
                    icon = rememberVectorPainter(image = Icons.Filled.PhotoSizeSelectActual)
                ) {
                    CardSettingsMenu(
                        settings = cardSettings,
                        updateCardStyle = viewModel::updateCardStyle,
                        updateScoreFormat = viewModel::updateScoreFormat,
                        updateShowProgressOnPlanned = viewModel::updateShowProgressOnPlanned,
                        updateShowScoreOnPlanned = viewModel::updateShowScoreOnPlanned,
                        updateShowApiIndicator = viewModel::updateShowApiIndicator
                    )
                }

                SettingsScreenItem(
                    title = "Tab Customization",
                    icon = rememberVectorPainter(image = Icons.Filled.Tab)
                ) {
                    TabCustomizationMenu(
                        state = state,
                        updateHiddenMovieStatuses = viewModel::updateHiddenMovieStatuses,
                        updateHiddenShowStatuses = viewModel::updateHiddenShowStatuses
                    )
                }
            }

            SettingsScreenSection(title = "Source Settings") {
                SettingsScreenItem(
                    title = "TMDB Settings",
                    icon = painterResource(id = R.drawable.tmdb),
                ) {
                    TMDBSettingsMenu(
                        settings = tmdbSettings,
                        updateEnabled = viewModel::updateTMDBEnabled,
                        updateAdult = viewModel::updateTMDBIncludeAdult
                    )
                }
                SettingsScreenItem(
                    title = "AniList Settings",
                    icon = painterResource(id = R.drawable.anilist),
                ) {
                    AnilistSettingsMenu(
                        settings = anilistSettings,
                        updateEnabled = viewModel::updateAnilistEnabled,
                        updatePreferredLanguage = viewModel::updateAnilistPreferredLanguage,
                        updateAdult = viewModel::updateAnilistIncludeAdult
                    )
                }
            }

            SettingsScreenSection(title = "Other Settings") {
                SettingsScreenItem(
                    title = "About WatchBuddy",
                    icon = rememberVectorPainter(image = Icons.Rounded.QuestionMark)
                ) {
                    Text(text = "About")
                }
            }
        }
    }
}

