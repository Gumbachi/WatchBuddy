package com.gumbachi.watchbuddy.ui.screens.movies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gumbachi.watchbuddy.model.enums.*
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.ui.theme.getElevation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(
    title: String,
    modifier: Modifier = Modifier,
    onCancelClicked: () -> Unit = {},
    defaultStatus: WatchStatus = WatchStatus.Watching,
    defaultScore: Double = 0.0,
    onEditSubmit: () -> Unit = {},
) {

    var status by remember { mutableStateOf(defaultStatus) }
    var score by remember { mutableStateOf(defaultScore) }

    var watchDropdownExpanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {}) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(28.dp))
                .background(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(getElevation(3))
                )
                .padding(24.dp)
        ) {
            // Title/Header
            EditDialogTitle(text = title)

            // WatchStatus Selector
            WatchStatusSelector(
                status = status,
                onStatusChange = { status = it }
            )

            NumberSelector(
                score = score,
                onScoreChange = { score = it }
            )

            EditDialogButtonRow(
                onCancelClicked = onCancelClicked
            )
        }
    }
}


@Composable
private fun EditDialogTitle(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {
        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
    },
    text: String = "Placeholder"
) {
    Row(
        modifier = modifier.padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        icon()
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
        )
    }
}

@Composable
private fun EditDialogSection(
    modifier: Modifier = Modifier,
    label: String = "Placeholder",
    content: @Composable () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun WatchStatusSelector(
    modifier: Modifier = Modifier,
    status: WatchStatus = WatchStatus.Watching,
    onStatusChange: (WatchStatus) -> Unit = {}
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    EditDialogSection(
        label = "Status",
        modifier = modifier
    ) {
        TextButton(
            contentPadding = PaddingValues(start = 24.dp, end = 16.dp),
            onClick = { expanded = true },
            modifier = Modifier.height(56.dp),
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(text = status.toString())
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open Status Selection Dropdown"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            WatchStatus.values().forEach {
                DropdownMenuItem(
                    text = { Text(text = it.toString()) },
                    onClick = {
                        onStatusChange(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NumberSelector(
    modifier: Modifier = Modifier,
    score: Double,
    onScoreChange: (Double) -> Unit = {},
    minValue: Double = 0.0,
    maxValue: Double = 10.0,
    scoreFormat: ScoreFormat = ScoreFormat.Decimal,
) {

    var textValue by remember {
        mutableStateOf(score.format(scoreFormat))
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    EditDialogSection(
        label = "Score",
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            OutlinedTextField(
                value = textValue,
                onValueChange = {
                    if (it.isEmpty()) textValue = it
                    if (it.validate(scoreFormat)) textValue = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                leadingIcon = {
                    IconButton(onClick = {
                        val newValue = textValue.format(scoreFormat) - 1
                        textValue = if (newValue in minValue..maxValue)
                            newValue.format(scoreFormat)
                        else
                            minValue.format(scoreFormat)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Decrease Score",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        val newValue = textValue.format(scoreFormat) + 1
                        textValue = if (newValue in minValue..maxValue)
                            newValue.format(scoreFormat)
                        else
                            maxValue.format(scoreFormat)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Increase Score",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                maxLines = 1,
                textStyle = TextStyle(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

@Composable
private fun EditDialogButtonRow(
    modifier: Modifier = Modifier,
    showDeleteButton: Boolean = true,
    onUpdateClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (showDeleteButton) {
            Button(
                onClick = onDeleteClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD2042D),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete this item"
                )
                Text(text = "Delete")
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilledIconButton(
                onClick = onCancelClicked,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFF0044FF)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "Cancel updates"
                )
            }
            FilledIconButton(
                onClick = onUpdateClicked,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFF228C22)
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Confirm updates"
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditDialog(
    darkMode: Boolean = true
) {
    WatchBuddyTheme(darkTheme = darkMode) {
        Surface {
            EditDialog(
                title = "Made in Abyss: The Golden City of the Scorching Sun"
            )
        }
    }
}