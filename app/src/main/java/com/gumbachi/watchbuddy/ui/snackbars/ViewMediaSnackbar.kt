package com.gumbachi.watchbuddy.ui.snackbars

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun SnackbarHostState.showViewMediaSnackbar(
    scope: CoroutineScope,
    message: String,
    crossinline navigateToMedia: () -> Unit
) {
    scope.launch {
        val result = showSnackbar(
            message = message,
            actionLabel = "View",
            withDismissAction = false,
            duration = SnackbarDuration.Short
        )

        if (result == SnackbarResult.ActionPerformed) navigateToMedia()
    }
}