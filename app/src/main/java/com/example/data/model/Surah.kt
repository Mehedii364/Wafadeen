package com.example.data.model

data class Surah(
    val number: Int,
    val nameAr: String,
    val nameEn: String,
    val nameBn: String,
    val meaningBn: String,
    val totalAyahs: Int,
    val revelationType: RevelationType,
    val audioUrl: String? = null
)

enum class RevelationType {
    MECCAN,
    MEDINAN
}

data class Ayah(
    val id: String,
    val surahNumber: Int,
    val ayahNumber: Int,
    val arabicText: String,
    val transliterationBn: String,
    val translationBn: String,
    val translationEn: String,
    val audioUrl: String? = null
)
