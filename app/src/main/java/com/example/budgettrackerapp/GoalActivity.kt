package com.example.budgettrackerapp
// Code Attribution
// Title: Add Category Feature using Room Database (Android)
// Author: Google Developers (Android Jetpack Team)
// Date: 2024
// Version: Android Jetpack (Room + Lifecycle + Coroutines)
// Available at: https://developer.android.com/training/data-storage/room
// Accessed: April 2026
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Goal
import kotlinx.coroutines.launch

class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        val minInput = findViewById<EditText>(R.id.minInput)
        val maxInput = findViewById<EditText>(R.id.maxInput)
        val saveBtn = findViewById<Button>(R.id.saveGoalBtn)

        // ✅ BACK BUTTON (ADD THIS)
        val backBtn = findViewById<Button>(R.id.backBtn)

        backBtn.setOnClickListener {
            finish() // go back to HomeActivity
        }

        val db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {
            val min = minInput.text.toString().toDoubleOrNull()
            val max = maxInput.text.toString().toDoubleOrNull()

            if (min == null || max == null) {
                Toast.makeText(this, "Enter valid values", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    db.goalDao().deleteAll()
                    db.goalDao().insert(Goal(minAmount = min, maxAmount = max))
                }

                Toast.makeText(this, "Goals saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}