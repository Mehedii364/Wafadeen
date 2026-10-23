package com.example.data.model

data class Hadith(
    val id: String,
    val collection: String, // e.g. "সহিহ বুখারী (Sahih al-Bukhari)"
    val chapterNameBn: String,
    val chapterNameEn: String,
    val hadithNumber: Int,
    val arabicMatn: String,
    val translationBn: String,
    val translationEn: String,
    val narratorBn: String,
    val narratorEn: String,
    val grading: String, // e.g. "সহিহ (Sahih)"
    val gradingSource: String, // e.g. "ইমাম বুখারী (Imam al-Bukhari)"
    val bookNumber: Int,
    val referenceText: String,
    val verificationStatus: VerificationStatus,
    val reviewer: String = "Islamic Foundation Bangladesh / Darussalam references",
    val reviewDate: String = "2025"
)

enum class VerificationStatus {
    VERIFIED,
    AWAITING_REVIEW
}
