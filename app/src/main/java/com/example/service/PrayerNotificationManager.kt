package com.example.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R
import com.example.data.model.PrayerTimes
import com.example.data.model.PrayerType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PrayerNotificationManager(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    init {
        createNotificationChannels()
    }

    /**
     * Initializes distinct notification channels for each specific Salah
     */
    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    "channel_prayer_fajr",
                    "ফজর সালাতের আজান (Fajr Adhan)",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "ফজরের নামাজের সময়সূচি ও আস-সালাতু খাইরুম মিনান নাওম সম্বলিত আজান"
                    enableVibration(true)
                },
                NotificationChannel(
                    "channel_prayer_sunrise",
                    "সূর্যোদয় ও ইশরাক স্মরণিকা (Sunrise)",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "সূর্যোদয় ও ইশরাক নামাজের স্মরণিকা"
                },
                NotificationChannel(
                    "channel_prayer_dhuhr",
                    "জোহর সালাতের আজান (Dhuhr Adhan)",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "জোহরের নামাজের সময়সূচি ও মক্কা মুকাররমার আজান"
                    enableVibration(true)
                },
                NotificationChannel(
                    "channel_prayer_asr",
                    "আসর সালাতের আজান (Asr Adhan)",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "আসরের নামাজের সময়সূচি ও মদিনা মুনাওয়ারার আজান"
                    enableVibration(true)
                },
                NotificationChannel(
                    "channel_prayer_maghrib",
                    "মাগরিব সালাতের আজান ও ইফতার (Maghrib)",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "মাগরিবের নামাজের সময়সূচি, ইফতার ও আল-আকসার আজান"
                    enableVibration(true)
                },
                NotificationChannel(
                    "channel_prayer_isha",
                    "এশা ও তারাবীহ সালাতের আজান (Isha)",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "এশার নামাজের সময়সূচি ও শাইখ আল-আফাসীর আজান"
                    enableVibration(true)
                }
            )

            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }

    /**
     * Shows a notification for a specific Salah with its distinct audio mapping
     */
    fun showPrayerNotification(
        prayerType: PrayerType,
        prayerTime: String,
        locationName: String
    ) {
        val mapping = SalahAudioRegistry.getAudioMappingForPrayer(prayerType)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            prayerType.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        )

        val title = when (prayerType) {
            PrayerType.FAJR -> "ফজরের নামাজের ওয়াক্ত শুরু হয়েছে"
            PrayerType.SUNRISE -> "সূর্যোদয়ের সময় হয়েছে (ইশরাক নামাজের প্রস্তুতি)"
            PrayerType.DHUHR -> "জোহরের নামাজের ওয়াক্ত শুরু হয়েছে"
            PrayerType.ASR -> "আসরের নামাজের ওয়াক্ত শুরু হয়েছে"
            PrayerType.MAGHRIB -> "মাগরিবের নামাজের ওয়াক্ত ও ইফতারের সময়"
            PrayerType.ISHA -> "এশার নামাজের ওয়াক্ত শুরু হয়েছে"
        }

        val extraFajrText = if (mapping.hasSpecialFajrPhrase) " • الصَّلَاةُ خَيْرٌ مِنَ النَّوْمِ" else ""
        val contentText = "${mapping.adhanStyleBn} • সময়: $prayerTime • $locationName$extraFajrText"

        val notification = NotificationCompat.Builder(context, mapping.channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(contentText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$contentText\n\n'নিশ্চয়ই নামাজ মুমিনদের ওপর নির্দিষ্ট সময়ে ফরজ।' (সূরা আন-নিসা: ১০৩)")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(prayerType.ordinal + 1000, notification)
        } catch (e: SecurityException) {
            // Missing POST_NOTIFICATIONS runtime permission on Android 13+
        }
    }

    /**
     * Schedules alarms for all daily prayer times using AlarmManager
     */
    fun scheduleAllDailyPrayers(prayerTimes: PrayerTimes, locationName: String) {
        val prayerPairs = listOf(
            PrayerType.FAJR to prayerTimes.fajr,
            PrayerType.SUNRISE to prayerTimes.sunrise,
            PrayerType.DHUHR to prayerTimes.dhuhr,
            PrayerType.ASR to prayerTimes.asr,
            PrayerType.MAGHRIB to prayerTimes.maghrib,
            PrayerType.ISHA to prayerTimes.isha
        )

        for ((type, timeStr) in prayerPairs) {
            scheduleSinglePrayerAlarm(type, timeStr, locationName)
        }
    }

    private fun scheduleSinglePrayerAlarm(
        prayerType: PrayerType,
        timeStr: String,
        locationName: String
    ) {
        val am = alarmManager ?: return
        val targetMillis = parseTimeToMillisTodayOrTomorrow(timeStr) ?: return

        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            putExtra(PrayerAlarmReceiver.EXTRA_PRAYER_TYPE, prayerType.name)
            putExtra(PrayerAlarmReceiver.EXTRA_PRAYER_TIME, timeStr)
            putExtra(PrayerAlarmReceiver.EXTRA_LOCATION_NAME, locationName)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            prayerType.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, targetMillis, pendingIntent)
            } else {
                am.setExact(AlarmManager.RTC_WAKEUP, targetMillis, pendingIntent)
            }
        } catch (e: SecurityException) {
            // In Android 12+, exact alarm permission might not be granted; fallback to standard set
            am.set(AlarmManager.RTC_WAKEUP, targetMillis, pendingIntent)
        }
    }

    fun cancelAllPrayerAlarms() {
        val am = alarmManager ?: return
        for (type in PrayerType.values()) {
            val intent = Intent(context, PrayerAlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                type.ordinal,
                intent,
                PendingIntent.FLAG_NO_CREATE or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
            )
            if (pendingIntent != null) {
                am.cancel(pendingIntent)
            }
        }
    }

    /**
     * Parses "hh:mm a" into a millisecond timestamp for today, or tomorrow if the time has already passed
     */
    fun parseTimeToMillisTodayOrTomorrow(timeStr: String): Long? {
        return try {
            val format = SimpleDateFormat("hh:mm a", Locale.US)
            val parsed = format.parse(timeStr.trim()) ?: return null

            val timeCal = Calendar.getInstance().apply { time = parsed }
            val targetCal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE))
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            // If the time already passed today, schedule for tomorrow
            if (targetCal.timeInMillis <= System.currentTimeMillis()) {
                targetCal.add(Calendar.DAY_OF_YEAR, 1)
            }

            targetCal.timeInMillis
        } catch (e: Exception) {
            null
        }
    }
}
