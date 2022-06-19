package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.tadjmerwan.niceweatherapp.models.response.GeoLocationResponse

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChanged: (TextFieldValue) -> Unit,
    searchTerm: TextFieldValue,
    suggestions: List<GeoLocationResponse?>,
    loadNewCallback: (GeoLocationResponse) -> Unit,
    clearInput: () -> Unit
) {
    Box() {
        OutlinedTextField(
            modifier = modifier,
            value = searchTerm,
            onValueChange = onValueChanged,
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
            },

            )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = !suggestions.isEmpty(),
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = { }
        ) {
            suggestions.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    onClick = {
                        if (item != null) {
                            loadNewCallback.invoke(item)
                            clearInput.invoke()
                        }
                    }, text = {
                        Text(
                            text = "${item?.name ?: ""}    ${item?.country ?: ""} - ${item?.state ?: ""}"
                        )
                    })

            }

        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(onValueChanged = {}, searchTerm = TextFieldValue(), suggestions = listOf(),
        loadNewCallback = {}
    ) {

    }
}