package com.example.data.model

data class Hadith(
    val id: String,
    val collection: String = "সহিহ বুখারী (Sahih al-Bukhari)",
    val bookNameAr: String,
    val bookNameBn: String,
    val hadithNumber: Int,
    val numberingSystem: String = "দারুসসালাম / Sunnah.com (Darussalam)",
    val narratorAr: String,
    val narratorBn: String,
    val arabicMatn: String,
    val banglaTransliteration: String,
    val banglaTranslation: String,
    val translationEn: String = "",
    val topic: String,
    val grading: String = "সহিহ (Sahih - মুত্তাফাক্ব আলাইহ)",
    val gradingSource: String = "ইমাম আল-বুখারী (Sahih al-Bukhari)",
    val bookNumber: Int,
    val referenceText: String,
    val sourceName: String = "সহিহ বুখারী (দারুসসালাম সংস্করণ)",
    val sourceUrl: String,
    val verificationStatus: VerificationStatus = VerificationStatus.VERIFIED,
    val reviewNotes: String = "মূল সহিহ বুখারী আরবি পাঠ ও ইসলামিক ফাউন্ডেশন বাংলাদেশ অনুবাদের সাথে যাচাইকৃত",
    val explanationBn: String? = null
) {
    val chapterNameBn: String get() = bookNameBn
    val translationBn: String get() = banglaTranslation
    val chapterNameEn: String get() = ""
    val narratorEn: String get() = ""
}

enum class VerificationStatus {
    VERIFIED,
    AWAITING_REVIEW,
    UNVERIFIED
}
