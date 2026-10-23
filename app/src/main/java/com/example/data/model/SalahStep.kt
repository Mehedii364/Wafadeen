package com.example.data.model

data class SalahGuideItem(
    val id: String,
    val titleBn: String,
    val titleEn: String,
    val titleAr: String,
    val category: SalahCategory,
    val arabicText: String? = null,
    val transliterationBn: String? = null,
    val meaningBn: String? = null,
    val meaningEn: String? = null,
    val explanationBn: String,
    val explanationEn: String,
    val isObligatory: Boolean = true,
    val hanafiNote: String? = null,
    val otherMadhhabsNote: String? = null,
    val reference: String? = null,
    val audioUrl: String? = null,
    val imageResId: Int? = null,
    val postureName: String? = null
)

enum class SalahCategory {
    PURIFICATION, // Wudu, Ghusl, Tayammum
    FIVE_PRAYERS, // Fajr, Dhuhr, Asr, Maghrib, Isha details & rak'at
    PRAYER_STEPS, // Takbir, Sana, Ruku, Sujud, Tashahhud, Salam
    SPECIAL_PRAYERS, // Jumu'ah, Janazah, Eid, Kasr, Sahw
    FIQH_DIFFERENCES // Madhhab differences, conditions, common mistakes
}

data class DailyPrayerStructure(
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val totalRakats: Int,
    val sunnahBefore: Int,
    val fard: Int,
    val sunnahAfter: Int,
    val nafl: Int = 0,
    val witr: Int = 0,
    val timeDescriptionBn: String,
    val timeDescriptionEn: String,
    val specialNotesBn: String? = null
)
