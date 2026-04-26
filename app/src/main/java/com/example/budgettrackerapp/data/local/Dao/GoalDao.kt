package com.example.budgettrackerapp.data.local.Dao

import androidx.room.*
import com.example.budgettrackerapp.data.local.entity.Goal

@Dao
interface GoalDao {

    @Insert
    suspend fun insert(goal: Goal)

    @Query("SELECT * FROM goals LIMIT 1")
    suspend fun getGoal(): Goal?

    @Update
    suspend fun update(goal: Goal)

    @Query("DELETE FROM goals")
    suspend fun deleteAll()
}