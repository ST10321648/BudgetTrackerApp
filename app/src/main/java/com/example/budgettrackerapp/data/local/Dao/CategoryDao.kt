package com.example.budgettrackerapp.data.local.Dao

import androidx.room.*
import com.example.budgettrackerapp.data.local.entity.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<Category>

    @Delete
    suspend fun delete(category: Category)
}