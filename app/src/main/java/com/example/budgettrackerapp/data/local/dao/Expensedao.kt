package com.example.budgettrackerapp.data.local.dao

import androidx.room.*
import com.example.budgettrackerapp.data.local.entity.CategoryTotal
import com.example.budgettrackerapp.data.local.entity.Expense

@Dao
interface ExpenseDao {

    // ➕ Insert expense
    @Insert
    suspend fun insert(expense: Expense)

    // 📋 Get all expenses
    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<Expense>

    // ✏️ Update expense
    @Update
    suspend fun update(expense: Expense)

    // ❌ Delete expense
    @Delete
    suspend fun delete(expense: Expense)

    // 📅 Filter by date range
    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getExpensesByDate(start: Long, end: Long): List<Expense>

    // 📊 Category totals (analytics)
    @Query("""
        SELECT c.name AS categoryName, SUM(e.amount) AS total
        FROM expenses e
        INNER JOIN categories c ON e.categoryId = c.categoryId
        GROUP BY e.categoryId
    """)
    suspend fun getCategoryTotals(): List<CategoryTotal>
}