package com.gumbachi.watchbuddy.components.dialogs.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun EditDialogItem(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = label, modifier = Modifier.weight(2F))
        Box(
            modifier = Modifier.weight(3F),
            contentAlignment = Alignment.CenterEnd
        ) {
            content()
        }
    }
}