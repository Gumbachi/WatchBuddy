package com.gumbachi.watchbuddy.ui.toolbars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumbachi.watchbuddy.ui.theme.WatchBuddyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchBuddySearchAppBar(
    hint: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    onSearch: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {

    var text by remember { mutableStateOf("") }

    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
            }
        },
        title = {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(vertical = 5.dp),
                placeholder = { Text(text = hint) },
                textStyle = MaterialTheme.typography.titleMedium,
                singleLine = true,
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(onClick = { text = "" }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Erase Text")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(text)
                        text = ""
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = Color.Transparent
                )
            )
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Filter Search Results")
            }
        }
    )
}

@Preview
@Composable
private fun DefaultPreview(dark: Boolean = true) {
    WatchBuddyTheme(darkTheme = dark) {
        Surface {
            WatchBuddySearchAppBar(hint = "Search Movies/Shows")
        }
    }
}

@Preview
@Composable
private fun DefaultPreviewLight() {
    DefaultPreview(dark = false)
}
