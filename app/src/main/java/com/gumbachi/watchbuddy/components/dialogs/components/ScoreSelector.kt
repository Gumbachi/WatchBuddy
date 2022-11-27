package com.gumbachi.watchbuddy.components.dialogs.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme
import com.gumbachi.watchbuddy.utils.toInt
import com.gumbachi.watchbuddy.utils.toString
import com.gumbachi.watchbuddy.utils.validate

@Composable
fun ScoreSelector(
    score: Int,
    scoreFormat: ScoreFormat,
    onScoreChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minScore: Int = 0,
    maxScore: Int = 100
) {

    val focusManager = LocalFocusManager.current
    val text = score.toString(scoreFormat, decorated = false)

    var value by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length)
            )
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Decrease Score Button
        IconButton(onClick = {
            val newScore = value.text.toInt(scoreFormat) - scoreFormat.changeAmount
            val updatedValue = if (newScore in minScore..maxScore) newScore else minScore
            value = TextFieldValue(updatedValue.toString(format = scoreFormat))
            onScoreChange(updatedValue)
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Decrease Score",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        BasicTextField(
            value = value,
            onValueChange = {
                if (it.text.isEmpty()) {
                    value = TextFieldValue(it.text, TextRange(it.text.length))
                    onScoreChange(minScore)
                } else if (it.text.validate(scoreFormat)) {
                    value = TextFieldValue(it.text, TextRange(it.text.length))
                    onScoreChange(it.text.toInt(scoreFormat))
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            maxLines = 1,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                    it()
                    Divider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                }
            }
        }



        IconButton(onClick = {
            val newScore = value.text.toInt(scoreFormat) + scoreFormat.changeAmount
            val updatedValue = if (newScore in minScore..maxScore) newScore else maxScore
            value = TextFieldValue(updatedValue.toString(scoreFormat, decorated = false))
            onScoreChange(updatedValue)
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "Increase Score",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun PreviewScoreSelector(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            ScoreSelector(score = 98, onScoreChange = {}, scoreFormat = ScoreFormat.Percentage)
        }
    }
}

@Preview
@Composable
private fun PreviewScoreSelectorLight() {
    PreviewScoreSelector(dark = false)
}