package com.gumbachi.watchbuddy.ui.screens.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    onSearch: (String) -> Unit = {},
    containerColor: Color = Color.Transparent
) {

    var text by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(vertical = 5.dp),
            shape = CircleShape,
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = hint,
                    color = Color.White.copy(alpha = 0.5F)
                )
            },
            textStyle = MaterialTheme.typography.titleMedium,
            singleLine = true,
            trailingIcon =  {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { text = "" }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
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
                containerColor = containerColor
            )
        )
    }
}
