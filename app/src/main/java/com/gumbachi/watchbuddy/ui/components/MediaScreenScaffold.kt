package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreenScaffold(
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

        // Loading Display
        if (isLoading) {
            LoadingDisplay(modifier = Modifier.padding(paddingValues))
        }

        // Dialogs display / Dialogs don't care about padding
        dialogs()

        // Error Display
        error?.let {
            ErrorDisplay(
                error = it,
                modifier = Modifier.padding(paddingValues)
            )
        }

        // Main Content
        Column(modifier = modifier.fillMaxSize().padding(paddingValues)) {
            tabRow()
            content()
        }
    }
}