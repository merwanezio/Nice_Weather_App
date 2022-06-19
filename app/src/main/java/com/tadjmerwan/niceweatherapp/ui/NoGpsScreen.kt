package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoGpsScreen(
    modifier: Modifier = Modifier,
    locationPermissionState: PermissionState,
    onRetry: () -> Unit
) {
    NoGpsElement(
        modifier.fillMaxSize(),
        shouldShowRationale = locationPermissionState.status.shouldShowRationale,
        onRetry
    )
}

@Composable
private fun NoGpsElement(
    modifier: Modifier = Modifier,
    shouldShowRationale: Boolean,
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textToShow = if (shouldShowRationale) {
            "Location is important for this app. Please grant the permission."
        } else {
            "Location permission required for this feature to be available. " +
                    "Please grant the permission"
        }
        Text(textToShow, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onRetry) {
            Text("Request permission")
        }
    }
}


@Preview(widthDp = 200, heightDp = 200, showBackground = true)
@Composable
private fun PreviewNoGpsScreenRationale() {
    NoGpsElement(
        shouldShowRationale = true
    )
}

@Preview(widthDp = 200, heightDp = 200, showBackground = true)
@Composable
private fun PreviewNoGpsScreenPleaseGrant() {
    NoGpsElement(shouldShowRationale = false)
}