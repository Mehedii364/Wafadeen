package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val id: String, // e.g. "quran_1_1" or "hadith_1" or "dua_morning_1"
    val contentType: String, // "QURAN", "HADITH", "DUA", "SALAH"
    val contentId: String,
    val title: String,
    val arabicText: String? = null,
    val translation: String? = null,
    val reference: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
