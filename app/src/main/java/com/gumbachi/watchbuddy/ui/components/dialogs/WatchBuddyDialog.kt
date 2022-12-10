package com.gumbachi.watchbuddy.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.IceSkating
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

enum class DialogIconPlacement {
    Above, Inline
}

@Composable
fun WatchBuddyDialog(
    title: String,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    icon: Painter? = null,
    iconLocation: DialogIconPlacement = DialogIconPlacement.Above,
    onDismissRequest: () -> Unit = {},
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    actions: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            horizontalAlignment = horizontalAlignment,
            modifier = modifier
                .widthIn(280.dp, 560.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(24.dp)

        ) {

            // Title Composable
            if (icon != null) {
                WatchBuddyDialogTitleWithIcon(
                    title = title,
                    titleStyle = titleStyle,
                    icon = icon,
                    iconLocation = iconLocation
                )
            } else {
                WatchBuddyDialogTitle(
                    title = title,
                    titleStyle = titleStyle
                )
            }

            // Content Composable
            val contentPadding = if (actions == null) 0.dp else 24.dp
            Column(modifier = Modifier.padding(bottom = contentPadding)) {
                content()
            }

            // Action Composable
            actions?.let {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    it()
                }
            }
        }
    }
}

@Composable
private fun WatchBuddyDialogTitle(
    title: String,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
) {
    Text(
        text = title,
        style = titleStyle,
        modifier = modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun WatchBuddyDialogTitleWithIcon(
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    iconLocation: DialogIconPlacement = DialogIconPlacement.Above,
) {
    when (iconLocation) {
        DialogIconPlacement.Above -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = title,
                    style = titleStyle
                )
            }
        }
        DialogIconPlacement.Inline -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Preview()
@Composable
private fun DefaultPreview(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            WatchBuddyDialog(
                title = "Title goes here its long",
                icon = rememberVectorPainter(image = Icons.TwoTone.IceSkating),
                iconLocation = DialogIconPlacement.Above,
                horizontalAlignment = Alignment.CenterHorizontally,
                actions = {
                    List(2) { "Action $it" }.forEach {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors()
                        ) {
                            Text(text = it)
                        }
                    }
                },
            ) {
                Text(text = "Line 1 ")
                Text(text = "Line 2")
                Text(text = "Line 3")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    DefaultPreview(dark = false)
}

