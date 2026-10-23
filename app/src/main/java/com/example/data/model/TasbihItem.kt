package com.example.data.model

data class TasbihDhikr(
    val id: String,
    val arabic: String,
    val transliterationBn: String,
    val meaningBn: String,
    val defaultTarget: Int = 33,
    val virtueBn: String
)

data class TasbihSession(
    val id: Long = 0,
    val dhikrTitle: String,
    val count: Int,
    val target: Int,
    val timestamp: Long,
    val formattedDate: String
)
