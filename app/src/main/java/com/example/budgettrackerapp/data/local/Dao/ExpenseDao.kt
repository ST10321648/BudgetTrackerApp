package com.example.budgettrackerapp.data.local.Dao

import androidx.room.*
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
}