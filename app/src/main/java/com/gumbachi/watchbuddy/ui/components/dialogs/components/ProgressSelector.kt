package com.gumbachi.watchbuddy.ui.components.dialogs.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme

@Composable
fun ProgressSelector(
    current: Int,
    min: Int,
    max: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    step: Int = 1,
    prefix: String? = null,
    suffix: String? = null
) {

    val focusManager = LocalFocusManager.current

    var text by remember {
        mutableStateOf(current.toString())
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Decrease Value Button
        IconButton(
            onClick = {
                focusManager.clearFocus()
                runCatching {
                    val newValue = current - step
                    if (newValue >= min) {
                        text = newValue.toString()
                        onValueChange(newValue)
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Decrease progress by $step",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        BasicTextField(
            value = text,
            onValueChange = { value ->
                runCatching { value.toInt() }
                    .onSuccess { num ->
                        if (num in min..max) {
                            text = num.toString()
                            onValueChange(num)
                        }
                    }.onFailure {
                        if (value.isEmpty()) {
                            text = ""
                            onValueChange(min)
                        }
                    }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (text.isEmpty()) { text = min.toString() }
                focusManager.clearFocus()
            }),
            maxLines = 1,
            textStyle = MaterialTheme.typography.labelLarge.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            visualTransformation = {
                TransformedText(
                    text = AnnotatedString("${prefix ?: ""}${it.text}${suffix ?: ""}"),
                    offsetMapping = object : OffsetMapping {
                        override fun originalToTransformed(offset: Int) = it.text.length
                        override fun transformedToOriginal(offset: Int) = it.text.length
                    }
                )
            }
        ) { content ->
            Row(modifier = Modifier.width(IntrinsicSize.Min)) {
                content()
            }
        }


        // Increase Value Button
        IconButton(
            onClick = {
                focusManager.clearFocus()
                runCatching {
                    val newValue = current + step
                    if (newValue <= max) {
                        text = newValue.toString()
                        onValueChange(newValue)
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "Increase progress by $step",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDark(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {

            var p by remember {
                mutableStateOf(5)
            }

            ProgressSelector(
                current = p,
                min = 0,
                max = 100,
                onValueChange = { p = it },
                suffix = " / 100"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    PreviewDark(dark = false)
}