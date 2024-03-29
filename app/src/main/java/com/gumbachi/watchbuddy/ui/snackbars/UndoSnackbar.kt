package com.gumbachi.watchbuddy.ui.snackbars

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun SnackbarHostState.showUndoSnackbar(
    scope: CoroutineScope,
    message: String,
    crossinline undo: () -> Unit
) {
    scope.launch {
        val result = showSnackbar(
            message = message,
            actionLabel = "Undo",
            withDismissAction = false,
            duration = SnackbarDuration.Short
        )

        if (result == SnackbarResult.ActionPerformed) undo()
    }
}