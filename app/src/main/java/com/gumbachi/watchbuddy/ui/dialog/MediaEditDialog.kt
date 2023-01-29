package com.gumbachi.watchbuddy.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.ui.components.NumberSelector
import com.gumbachi.watchbuddy.ui.components.SingleSelectDropdown
import com.gumbachi.watchbuddy.ui.components.SpacedSection
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.utils.MediaDummy
import com.gumbachi.watchbuddy.utils.toInt
import com.gumbachi.watchbuddy.utils.toString

@Composable
fun MediaEditDialog(
    media: Media?,
    title: String,
    isMediaSaved: Boolean,
    scoreFormat: ScoreFormat,
    onDismissRequest: () -> Unit,
    onConfirm: (Media) -> Unit,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = onDismissRequest,
    hiddenStatuses: Collection<WatchStatus> = emptyList(),
    showProgressSection: Boolean = true,
    onMediaDelete: () -> Unit = {},
) {

    if (media == null) return
    val state by remember { mutableStateOf(media) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onConfirm(state) }) {
                if (isMediaSaved)
                    Text(text = "Update")
                else
                    Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        },
        icon = {
            Icon(
                imageVector = if (isMediaSaved) Icons.Filled.Edit else Icons.Filled.SaveAs,
                contentDescription = null
            )
        },
        title = { Text(text = title) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SpacedSection(label = "Status") {
                    SingleSelectDropdown(
                        selected = state.watchStatus,
                        onSelectedChange = { state.watchStatus = it },
                        options = WatchStatus.values().toSet() - hiddenStatuses.toSet(),
                        colors = ButtonDefaults.buttonColors()
                    )
                }
                SpacedSection(label = "Score") {
                    NumberSelector(
                        initialValue = state.userScore,
                        min = 0,
                        max = 100,
                        onValueChange = { state.userScore = it },
                        suffix = " / ${100.toString(scoreFormat, decorated = true)}",
                        step = scoreFormat.step,
                        valueToString = { it.toString(format = scoreFormat) },
                        valueToInt = { it.toInt(format = scoreFormat) }
                    )
                }
                if (showProgressSection) {
                    SpacedSection(label = "Progress") {
                        NumberSelector(
                            initialValue = state.episodesWatched,
                            min = 0,
                            max = state.totalEpisodes ?: Int.MAX_VALUE,
                            onValueChange = { state.episodesWatched = it },
                            suffix = " / ${state.totalEpisodes ?: "?"}"
                        )
                    }
                }
                SpacedSection(label = "Start Date") {
                    OutlinedButton(onClick = { /*TODO DATE*/ }) {
                        Text(
                            text = if (state.startDate == null) {
                                "--/--/----"
                            } else {
                                state.startDate.toString()
                            }
                        )
                    }
                }
                SpacedSection(label = "Finish Date") {
                    OutlinedButton(onClick = { /*TODO DATE*/ }) {
                        Text(
                            text = if (state.finishDate == null) {
                                "--/--/----"
                            } else {
                                state.finishDate.toString()
                            }
                        )
                    }
                }
                if (isMediaSaved) {
                    Button(
                        onClick = onMediaDelete,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Red
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Delete Media")
                    }
                }
            }
        },
    )
}

@Preview
@Composable
private fun Preview(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            MediaEditDialog(
                media = MediaDummy.NormalShow(),
                title = "Edit Media",
                isMediaSaved = true,
                scoreFormat = ScoreFormat.Percentage,
                onDismissRequest = { /*TODO*/ },
                onConfirm = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    Preview(dark = false)
}