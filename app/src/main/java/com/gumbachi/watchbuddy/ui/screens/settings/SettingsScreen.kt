package com.gumbachi.watchbuddy.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gumbachi.watchbuddy.ui.screens.settings.components.CardCustomizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val scrollState = rememberScrollState()

    Scaffold(modifier = modifier) { innerPadding ->

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = "Card Style",
                style = MaterialTheme.typography.titleLarge
            )

            CardCustomizer(
                cardStyle = state.settings.cardStyle,
                onCardStyleChange = { viewModel.onCardStyleUpdate(it) },
                scoreFormat = state.settings.scoreFormat,
                onScoreFormatChange = { viewModel.onScoreFormatUpdate(it) }
            )
        }
    }
}

