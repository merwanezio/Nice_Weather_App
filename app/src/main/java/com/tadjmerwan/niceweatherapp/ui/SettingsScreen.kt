package com.tadjmerwan.niceweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tadjmerwan.niceweatherapp.Config
import com.tadjmerwan.niceweatherapp.models.MeasurementUnits


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    changeUnitCallback: (MeasurementUnits) -> Unit,
    units: MeasurementUnits
) {
    val units = remember { mutableStateOf(units) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Settings ",
            color = Color.Gray,

            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Units Used   ",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                if (units.value == MeasurementUnits.Metric)
                    units.value = MeasurementUnits.Imperial
                else
                    units.value = MeasurementUnits.Metric
                changeUnitCallback.invoke(units.value)
            }) {
                Text(text = units.value.name)
            }

        }

    }
}


@Preview
@Composable
private fun ErrorSettingsScreen() {
    SettingsScreen(Modifier, units = Config.DEFAULT_UNITS, changeUnitCallback = {

    })
}


