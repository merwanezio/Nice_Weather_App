package com.tadjmerwan.niceweatherapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmRec : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "MORNING_FORECAST") {
        }
    }
}