package com.example.budgettrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Category

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val addCategoryBtn = findViewById<Button>(R.id.addCategoryBtn)
        val addExpenseBtn = findViewById<Button>(R.id.addExpenseBtn)
        val viewExpensesBtn = findViewById<Button>(R.id.viewExpensesBtn)

        // Navigates to Add Category screen
        addCategoryBtn.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        // Navigates to Add Expense screen
        addExpenseBtn.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        // Navigates to View Expenses screen
        viewExpensesBtn.setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        val setGoalBtn = findViewById<Button>(R.id.setGoalBtn)

        setGoalBtn.setOnClickListener {
            startActivity(Intent(this, GoalActivity::class.java))
        }

        val db = AppDatabase.getDatabase(this)

        val minTextView = findViewById<TextView>(R.id.minTextView)
        val maxTextView = findViewById<TextView>(R.id.maxTextView)

        lifecycleScope.launch {
            val goal = db.goalDao().getGoal()

            if (goal != null) {
                minTextView.text = "Min: ${goal.minAmount}"
                maxTextView.text = "Max: ${goal.maxAmount}"
            }
        }
    }
}