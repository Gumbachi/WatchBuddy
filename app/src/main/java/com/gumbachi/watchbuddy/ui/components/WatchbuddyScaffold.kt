package com.gumbachi.watchbuddy.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchbuddyScaffold(
    isLoading: Boolean,
    error: Throwable?,
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    tabRow: @Composable () -> Unit = {},
    dialogs: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        snackbarHost = snackbarHost
    ) { paddingValues ->


        AnimatedVisibility(
            visible = isLoading,
            exit = shrinkVertically(shrinkTowards = Alignment.Top) { 0 }
        ) {
            LoadingDisplay()
        }

        AnimatedVisibility(
            visible = !isLoading,
            enter = expandVertically { 0 }
        ) {
            // Main Content
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                tabRow()
                content()
            }
        }

        // Dialogs display / Dialogs don't care about padding
        dialogs()

        AnimatedVisibility(
            visible = error != null,
        ) {
            error?.let {
                ErrorDisplay(
                    error = it,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}