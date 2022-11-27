package com.gumbachi.watchbuddy.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.gumbachi.watchbuddy.components.dialogs.components.EditDialogItem
import com.gumbachi.watchbuddy.components.dialogs.components.ScoreSelector
import com.gumbachi.watchbuddy.components.dialogs.components.WatchStatusSelector
import com.gumbachi.watchbuddy.model.EditableState
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.interfaces.SearchResult

@Composable
fun MediaSaveDialog(
    title: String,
    searchResult: SearchResult,
    scoreFormat: ScoreFormat,
    modifier: Modifier = Modifier,
    onSubmit: (EditableState) -> Unit = {},
    onCancel: () -> Unit = {}
) {

    var state by remember {
        mutableStateOf(EditableState from searchResult)
    }

    WatchBuddyDialog(
        title = title,
        modifier = modifier,
        icon = rememberVectorPainter(image = Icons.Filled.SaveAs),
        actions = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel")
            }
            TextButton(onClick = { onSubmit(state) }) {
                Text(text = "Save")
            }
        }
    ) {

        EditDialogItem(label = "Status") {
            WatchStatusSelector(
                status = state.watchStatus,
                onStatusChange = { state =  state.copy(watchStatus = it) },
            )
        }

        EditDialogItem(label = "Score") {
            ScoreSelector(
                score = state.userScore,
                scoreFormat = scoreFormat,
                onScoreChange = { state = state.copy(userScore = it) }
            )
        }
    }
}
