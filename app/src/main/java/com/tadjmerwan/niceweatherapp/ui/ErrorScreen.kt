package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, text: String, refreshCallback: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Error !",
            color = Color.Red,
            fontSize = 44.sp
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text,
            fontSize = 24.sp
        )
        Button(onClick = refreshCallback) {
            Text(text = "Retry")
        }
    }
}


@Preview
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(text = "Test Error") {

    }
}