package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import kotlinx.coroutines.launch

class ViewGoalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_goals)

        val minTextView = findViewById<TextView>(R.id.minTextView)
        val maxTextView = findViewById<TextView>(R.id.maxTextView)

        // ✅ BACK BUTTON (this is what you were missing)
        val backBtn = findViewById<Button>(R.id.backBtn)

        backBtn.setOnClickListener {
            finish() // returns to previous screen
        }

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val goal = db.goalDao().getGoal()

            if (goal != null) {
                minTextView.text = "Min: ${goal.minAmount}"
                maxTextView.text = "Max: ${goal.maxAmount}"
            } else {
                minTextView.text = "Min: -"
                maxTextView.text = "Max: -"
            }
        }
    }
}