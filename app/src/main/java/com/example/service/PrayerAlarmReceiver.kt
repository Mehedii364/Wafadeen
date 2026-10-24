package com.example.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.data.model.PrayerType

class PrayerAlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_PRAYER_TYPE = "com.example.wafadeen.EXTRA_PRAYER_TYPE"
        const val EXTRA_PRAYER_TIME = "com.example.wafadeen.EXTRA_PRAYER_TIME"
        const val EXTRA_LOCATION_NAME = "com.example.wafadeen.EXTRA_LOCATION_NAME"
        private const val TAG = "PrayerAlarmReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val prayerTypeName = intent.getStringExtra(EXTRA_PRAYER_TYPE) ?: return
        val prayerTime = intent.getStringExtra(EXTRA_PRAYER_TIME) ?: ""
        val locationName = intent.getStringExtra(EXTRA_LOCATION_NAME) ?: "আপনার এলাকা"

        val prayerType = try {
            PrayerType.valueOf(prayerTypeName)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Unknown prayer type: $prayerTypeName")
            return
        }

        Log.d(TAG, "Received alarm for prayer: $prayerType at $prayerTime for $locationName")

        val notificationManager = PrayerNotificationManager(context)
        notificationManager.showPrayerNotification(
            prayerType = prayerType,
            prayerTime = prayerTime,
            locationName = locationName
        )
    }
}
