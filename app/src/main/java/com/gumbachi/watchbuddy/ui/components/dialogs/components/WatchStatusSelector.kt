package com.gumbachi.watchbuddy.ui.components.dialogs.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun WatchStatusSelector(
    watchStatus: WatchStatus,
    onStatusChange: (WatchStatus) -> Unit,
    modifier: Modifier = Modifier,
    options: Collection<WatchStatus> = WatchStatus.values().toList(),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {

    var status by remember { mutableStateOf(watchStatus) }
    var expanded by remember { mutableStateOf(false) }
    val dropdownIcon by remember(expanded) {
        derivedStateOf {
            when (expanded) {
                true -> Icons.Filled.ArrowDropUp
                false -> Icons.Filled.ArrowDropDown
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { expanded = !expanded },
            contentPadding = PaddingValues(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = Color.Transparent
            ),
            modifier = modifier
        ) {
            Text(
                text = status.toString(),
                style = textStyle,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Icon(
                imageVector = dropdownIcon,
                contentDescription = "Open/Close Status Dropdown"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.toString()) },
                    onClick = {
                        status = it
                        onStatusChange(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            WatchStatusSelector(
                watchStatus = WatchStatus.Watching,
                onStatusChange = {},
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(false)
}