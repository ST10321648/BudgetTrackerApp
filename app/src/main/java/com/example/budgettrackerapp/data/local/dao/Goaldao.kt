package com.example.budgettrackerapp.data.local.dao

import androidx.room.*
import com.example.budgettrackerapp.data.local.entity.Goal

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: Goal)

    @Query("SELECT * FROM goals LIMIT 1")
    suspend fun getGoal(): Goal?

    // Ayabonga: Updated to use maxAmount from your Entity
    @Query("SELECT maxAmount FROM goals LIMIT 1")
    suspend fun getMonthlyGoal(): Double?
    @Update
    suspend fun update(goal: Goal)

    @Query("DELETE FROM goals")
    suspend fun deleteAll()
}