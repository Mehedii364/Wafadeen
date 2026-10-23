package com.example.data.model

data class PrayerTimes(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val dateFormatted: String,
    val hijriDate: String,
    val calculationMethod: String = "Islamic Foundation Bangladesh / University of Islamic Sciences, Karachi",
    val asrJuristicMethod: String = "Hanafi"
)

data class PrayerItem(
    val type: PrayerType,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val timeString: String,
    val isCurrent: Boolean = false,
    val isNext: Boolean = false
)

enum class PrayerType {
    FAJR,
    SUNRISE,
    DHUHR,
    ASR,
    MAGHRIB,
    ISHA
}

data class CityLocation(
    val nameBn: String,
    val nameEn: String,
    val latitude: Double,
    val longitude: Double,
    val timeZoneOffsetHours: Double,
    val country: String
)
