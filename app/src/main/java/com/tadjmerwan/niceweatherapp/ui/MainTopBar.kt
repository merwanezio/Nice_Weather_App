package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    actionCallback: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text("NiceWeather™") },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = actionCallback) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewMainTopBar() {
    MainTopBar(
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        {}
    )
}