package com.gumbachi.watchbuddy.ui.snackbars

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

suspend fun SnackbarHostState.showViewMediaSnackbar(
    message: String,
    navigateToMedia: () -> Unit
) {
    val result = showSnackbar(
        message = message,
        actionLabel = "View",
        withDismissAction = false,
        duration = SnackbarDuration.Short
    )

    if (result == SnackbarResult.ActionPerformed) navigateToMedia()
}