package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Goal

class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        val minInput = findViewById<EditText>(R.id.minInput)
        val maxInput = findViewById<EditText>(R.id.maxInput)
        val saveBtn = findViewById<Button>(R.id.saveGoalBtn)

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