package com.gumbachi.watchbuddy.ui.screens.search

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun SearchResultLabel(
    unemphasizedText: String,
    emphasizedText: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        buildAnnotatedString {
            append(unemphasizedText)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(emphasizedText)
            }
        },
        textAlign = textAlign,
        style = style,
        modifier = modifier,
    )
}