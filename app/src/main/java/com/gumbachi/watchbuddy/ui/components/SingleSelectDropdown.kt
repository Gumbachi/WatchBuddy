package com.gumbachi.watchbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun <T> SingleSelectDropdown(
    selected: T,
    onSelectedChange: (T) -> Unit,
    options: Collection<T>,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurface,
    )
) {

    var current by remember { mutableStateOf(selected) }
    var expanded by remember { mutableStateOf(false) }
    val dropdownIcon by remember(expanded) {
        derivedStateOf {
            when (expanded) {
                true -> Icons.Filled.ArrowDropUp
                false -> Icons.Filled.ArrowDropDown
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(IntrinsicSize.Min)
    ) {
        ElevatedButton(
            onClick = { expanded = !expanded },
            contentPadding = PaddingValues(horizontal = 4.dp),
            colors = colors,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.width(130.dp)
        ) {
            Text(
                text = current.toString(),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Icon(
                imageVector = dropdownIcon,
                contentDescription = "Open/Close Dropdown"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(130.dp),

            ) {
            options.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.toString(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        current = it
                        onSelectedChange(it)
                        expanded = false
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDark() {
    WatchBuddyTheme(darkTheme = true) {
        Surface {
            SingleSelectDropdown(
                selected = WatchStatus.Watching,
                onSelectedChange = {},
                options = WatchStatus.values().toList()
            )
        }
    }
}