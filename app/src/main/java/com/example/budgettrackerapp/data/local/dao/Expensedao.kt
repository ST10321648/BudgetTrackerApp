package com.example.budgettrackerapp.data.local.dao

import androidx.room.*
import com.example.budgettrackerapp.data.local.entity.CategoryTotal
import com.example.budgettrackerapp.data.local.entity.Expense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<Expense>

    // Ayabonga: This fix calculates the total for your HomeActivity progress bar
    @Query("SELECT SUM(amount) FROM expenses")
    suspend fun getTotalSpending(): Double?

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getExpensesByDate(start: Long, end: Long): List<Expense>

    @Query("""
        SELECT c.name AS categoryName, SUM(e.amount) AS total
        FROM expenses e
        INNER JOIN categories c ON e.categoryId = c.categoryId
        GROUP BY e.categoryId
    """)
    suspend fun getCategoryTotals(): List<CategoryTotal>
}