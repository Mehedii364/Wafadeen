package com.example.data.model

data class Dua(
    val id: String,
    val titleBn: String,
    val titleEn: String,
    val category: DuaCategory,
    val type: DuaType,
    val arabicText: String,
    val transliterationBn: String,
    val meaningBn: String,
    val meaningEn: String,
    val contextBn: String,
    val reference: String,
    val repetitionCount: Int = 1,
    val audioUrl: String? = null,
    val isVerified: Boolean = true
)

enum class DuaCategory {
    MORNING_EVENING, // সকাল ও সন্ধ্যার আজকার
    AFTER_SALAH,     // নামাজের পরের দোয়া
    DAILY_LIFE,      // দৈনন্দিন জীবনের দোয়া (ঘুম, আহার, ঘর থেকে বের হওয়া ইত্যাদি)
    ISTIGHFAR,       // তওবা ও ইস্তিগফার (সাইয়্যিদুল ইস্তিগফার)
    QURANIC_RABBANA, // কুরআনে বর্ণিত রব্বানা দোয়া
    TRAVEL_SAFETY,   // সফর ও বিপদাপদের দোয়া
    ILLNESS_HEALING  // রোগমুক্তি ও শেফা
}

enum class DuaType {
    QURANIC,           // কুরআনি দোয়া
    PROPHETIC_SUNNAH,  // রাসুলুল্লাহ (সা.)-এর সুন্নাহ ও সহিহ হাদিস
    PERMISSIBLE_SUPPLICATION // মুস্তাহাব / অনুমোদিত মোনাজাত
}
