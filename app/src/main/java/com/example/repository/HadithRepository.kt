package com.example.repository

import com.example.data.hadith.BukhariHadithsPart1
import com.example.data.hadith.BukhariHadithsPart2
import com.example.data.hadith.BukhariHadithsPart3
import com.example.data.hadith.BukhariHadithsPart4
import com.example.data.model.Hadith
import java.util.Calendar

object HadithRepository {

    /**
     * Complete collection of 200 authentic Sahih al-Bukhari Hadiths,
     * fully verified from Sahih al-Bukhari (Darussalam / Sunnah.com edition).
     */
    val hadiths: List<Hadith> = BukhariHadithsPart1.list +
        BukhariHadithsPart2.list +
        BukhariHadithsPart3.list +
        BukhariHadithsPart4.list

    /**
     * Unique categorized topics across all 200 Hadiths.
     */
    val allTopics: List<String> by lazy {
        listOf("সকল") + hadiths.map { it.topic }.distinct()
    }

    /**
     * Retrieves a daily Hadith consistently determined by the current day of year.
     */
    fun getDailyHadith(): Hadith {
        if (hadiths.isEmpty()) {
            throw IllegalStateException("Hadith collection is empty")
        }
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val index = (dayOfYear - 1).coerceAtLeast(0) % hadiths.size
        return hadiths[index]
    }

    fun getHadithById(id: String): Hadith? {
        return hadiths.firstOrNull { it.id == id }
    }

    fun getHadithsByTopic(topic: String): List<Hadith> {
        if (topic.isBlank() || topic == "সকল" || topic.equals("All", ignoreCase = true)) {
            return hadiths
        }
        return hadiths.filter { it.topic == topic }
    }

    /**
     * Full-text search across Bengali translations, transliterations, Arabic matn,
     * narrators, book names, numbers, and topics with optional category filter.
     */
    fun searchHadiths(query: String, selectedTopic: String? = null): List<Hadith> {
        val baseList = if (selectedTopic.isNullOrBlank() || selectedTopic == "সকল" || selectedTopic.equals("All", ignoreCase = true)) {
            hadiths
        } else {
            hadiths.filter { it.topic == selectedTopic }
        }

        if (query.isBlank()) return baseList

        val trimmed = query.trim()
        val q = trimmed.lowercase()

        return baseList.filter { hadith ->
            hadith.hadithNumber.toString() == trimmed ||
            hadith.banglaTranslation.lowercase().contains(q) ||
            hadith.banglaTransliteration.lowercase().contains(q) ||
            hadith.arabicMatn.contains(trimmed) ||
            hadith.narratorBn.lowercase().contains(q) ||
            hadith.narratorAr.contains(trimmed) ||
            hadith.bookNameBn.lowercase().contains(q) ||
            hadith.bookNameAr.contains(trimmed) ||
            hadith.topic.lowercase().contains(q) ||
            hadith.translationEn.lowercase().contains(q) ||
            (hadith.explanationBn != null && hadith.explanationBn.lowercase().contains(q)) ||
            hadith.referenceText.lowercase().contains(q)
        }
    }
}
