package com.example.budgettrackerapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Int = 0,
    val minAmount: Double,
    val maxAmount: Double
)