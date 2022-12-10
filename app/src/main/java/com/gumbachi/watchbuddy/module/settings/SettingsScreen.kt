package com.gumbachi.watchbuddy.module.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Settings") })
        }
    ) { innerPadding ->

        // Loading settings
        if (state.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Divider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {


            Text(
                text = "Customize Card",
                style = MaterialTheme.typography.labelLarge
            )

            CardCustomizer(
                cardStyle = state.settings.cardStyle,
                onCardStyleChange = { viewModel.updateCardStyleTo(it) },
                scoreFormat = state.settings.scoreFormat,
                onScoreFormatChange = { viewModel.updateScoreFormatTo(it) }
            )

            Divider(Modifier.padding(vertical = 4.dp))

            Text(
                text = "Customize Movie Status Tabs",
                style = MaterialTheme.typography.labelLarge
            )

            WatchStatusTabCustomizer(
                hiddenCategories = state.settings.hiddenMovieStatuses,
                onHiddenCategoriesChange = { viewModel.updateHiddenMovieStatuses(it.toSet()) }
            )

            Divider(Modifier.padding(vertical = 4.dp))

            Text(
                text = "Customize Show Status Tabs",
                style = MaterialTheme.typography.labelLarge
            )

            WatchStatusTabCustomizer(
                hiddenCategories = state.settings.hiddenShowStatuses,
                onHiddenCategoriesChange = { viewModel.updateHiddenShowStatuses(it.toSet()) }
            )

            Divider(Modifier.padding(vertical = 4.dp))

        }
    }
}

