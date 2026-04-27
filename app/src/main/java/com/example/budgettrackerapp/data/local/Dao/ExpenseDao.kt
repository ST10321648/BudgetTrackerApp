package com.example.budgettrackerapp.data.local.Dao

import androidx.room.*
import com.example.budgettrackerapp.data.local.entity.CategoryTotal
import com.example.budgettrackerapp.data.local.entity.Expense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<Expense>

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getByDate(start: String, end: String): List<Expense>

    @Query("""
    SELECT c.name AS categoryName, SUM(e.amount) AS total
    FROM expenses e
    INNER JOIN categories c ON e.categoryId = c.categoryId
    GROUP BY e.categoryId
""")
    suspend fun getCategoryTotals(): List<CategoryTotal>

}