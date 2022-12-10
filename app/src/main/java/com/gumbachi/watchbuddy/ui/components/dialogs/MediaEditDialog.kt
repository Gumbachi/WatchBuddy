package com.gumbachi.watchbuddy.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.interfaces.Editable
import com.gumbachi.watchbuddy.ui.components.common.MaxWidthCenteredRow
import com.gumbachi.watchbuddy.ui.components.dialogs.components.EditDialogItem
import com.gumbachi.watchbuddy.ui.components.dialogs.components.ScoreSelector
import com.gumbachi.watchbuddy.ui.components.dialogs.components.WatchStatusSelector

@Composable
fun MediaEditDialog(
    title: String,
    editable: Editable,
    scoreFormat: ScoreFormat,
    modifier: Modifier = Modifier,
    onSubmit: (Editable) -> Unit = {},
    onCancel: () -> Unit = {},
    onDelete: () -> Unit = {}
) {

    val state by remember { mutableStateOf(editable) }

    WatchBuddyDialog(
        title = title,
        modifier = modifier,
        icon = rememberVectorPainter(image = Icons.Filled.Edit),
        actions = {
            MaxWidthCenteredRow(horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(text = "Delete")
                }
                Row {
                    TextButton(onClick = onCancel) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = { onSubmit(state) }) {
                        Text(text = "Update")
                    }
                }
            }
        }
    ) {

        EditDialogItem(label = "Status") {
            WatchStatusSelector(
                watchStatus = state.watchStatus,
                onStatusChange = { state.watchStatus = it},
            )
        }

        EditDialogItem(label = "Score") {
            ScoreSelector(
                score = state.userScore,
                scoreFormat = scoreFormat,
                onScoreChange = { state.userScore = it }
            )
        }
    }
}
