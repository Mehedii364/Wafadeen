package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasbih_history")
data class TasbihEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dhikrTitle: String,
    val count: Int,
    val target: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val formattedDate: String
)
