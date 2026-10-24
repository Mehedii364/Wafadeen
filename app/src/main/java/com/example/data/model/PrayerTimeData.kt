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
    val calculationMethod: String = "ইসলামিক ফাউন্ডেশন বাংলাদেশ / করাচি ১৮° পদ্ধতি",
    val asrJuristicMethod: String = "হানাফি (দ্বিগুণ ছায়া)"
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

enum class CalculationMethod(
    val id: String,
    val nameBn: String,
    val nameEn: String,
    val fajrAngle: Double,
    val ishaAngle: Double,
    val isFixedIshaMinutes: Boolean = false,
    val ishaMinutesAfterMaghrib: Int = 90
) {
    KARACHI_IFB(
        id = "karachi_ifb",
        nameBn = "ইসলামিক ফাউন্ডেশন বাংলাদেশ / করাচি (১৮°/১৮°)",
        nameEn = "Islamic Foundation Bangladesh / Karachi (18°/18°)",
        fajrAngle = 18.0,
        ishaAngle = 18.0
    ),
    MWL(
        id = "mwl",
        nameBn = "মুসলিম ওয়ার্ল্ড লীগ (১৮°/১৭°)",
        nameEn = "Muslim World League (18°/17°)",
        fajrAngle = 18.0,
        ishaAngle = 17.0
    ),
    ISNA(
        id = "isna",
        nameBn = "ইসলামিক সোসাইটি অব নর্থ আমেরিকা (১৫°/১৫°)",
        nameEn = "Islamic Society of North America (15°/15°)",
        fajrAngle = 15.0,
        ishaAngle = 15.0
    ),
    UMM_AL_QURA(
        id = "umm_al_qura",
        nameBn = "উম্মুল কুরা, মক্কা মুকাররমা (১৮.৫°/৯০ মিনিট)",
        nameEn = "Umm al-Qura University, Makkah (18.5°/90 min)",
        fajrAngle = 18.5,
        ishaAngle = 0.0,
        isFixedIshaMinutes = true,
        ishaMinutesAfterMaghrib = 90
    ),
    EGYPTIAN(
        id = "egyptian",
        nameBn = "মিশরীয় জেনারেল অথরিটি অব সার্ভে (১৯.৫°/১৭.৫°)",
        nameEn = "Egyptian General Authority of Survey (19.5°/17.5°)",
        fajrAngle = 19.5,
        ishaAngle = 17.5
    )
}

enum class HighLatitudeRule(
    val id: String,
    val nameBn: String,
    val nameEn: String
) {
    ANGLE_BASED("angle_based", "কোণ অনুপাত পদ্ধতি (Angle Based)", "Angle Based"),
    ONE_SEVENTH("one_seventh", "রাতের এক-সপ্তমাংশ পদ্ধতি (1/7th of Night)", "1/7th of Night"),
    MIDDLE_OF_NIGHT("middle_of_night", "মধ্যরাত পদ্ধতি (Middle of Night)", "Middle of Night")
}

data class SalahAudioMapping(
    val prayerType: PrayerType,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val channelId: String,
    val audioId: String,
    val audioUrl: String,
    val adhanStyleBn: String,
    val adhanStyleEn: String,
    val hasSpecialFajrPhrase: Boolean = false
)

data class CityLocation(
    val nameBn: String,
    val nameEn: String,
    val latitude: Double,
    val longitude: Double,
    val timeZoneOffsetHours: Double,
    val country: String
)
