package com.gumbachi.watchbuddy.components.dialogs.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun WatchStatusSelector(
    status: WatchStatus,
    onStatusChange: (WatchStatus) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {

    var expanded by remember { mutableStateOf(false) }

    val dropdownIcon = when (expanded) {
        true -> Icons.Filled.ArrowDropUp
        false -> Icons.Filled.ArrowDropDown
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(
            onClick = { expanded = !expanded },
            shape = MaterialTheme.shapes.extraSmall,
            contentPadding = PaddingValues(horizontal = 4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
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

@Preview
@Composable
fun DefaultPreview(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            WatchStatusSelector(
                status = WatchStatus.Repeating,
                onStatusChange = {},
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLight() {
    DefaultPreview(false)
}