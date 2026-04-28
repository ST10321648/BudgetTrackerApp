package com.example.budgettrackerapp.data.local.entity

import androidx.room.*

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoryId")]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val amount: Double,
    val description: String,
    val categoryId: Int,
    val date: Long,   // 🔥 IMPORTANT (matches filter now)         // ✅ default value
    val photoUri: String? = null,
    val imageUri: String?
)