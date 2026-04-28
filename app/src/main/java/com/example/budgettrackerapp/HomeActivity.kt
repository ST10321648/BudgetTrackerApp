package com.example.budgettrackerapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // =========================
        // 📌 NAVIGATION BUTTONS
        // =========================
        val addCategoryBtn = findViewById<Button>(R.id.addCategoryBtn)
        val addExpenseBtn = findViewById<Button>(R.id.addExpenseBtn)
        val viewExpensesBtn = findViewById<Button>(R.id.viewExpensesBtn)
        val setGoalBtn = findViewById<Button>(R.id.setGoalBtn)
        val viewGoalsBtn = findViewById<Button>(R.id.viewGoalsBtn)
        val viewTotalsBtn = findViewById<Button>(R.id.viewCategoryTotalsBtn)

        addCategoryBtn.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

        addExpenseBtn.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        viewExpensesBtn.setOnClickListener {
            startActivity(Intent(this, ViewExpensesActivity::class.java))
        }

        setGoalBtn.setOnClickListener {
            startActivity(Intent(this, GoalActivity::class.java))
        }

        viewGoalsBtn.setOnClickListener {
            startActivity(Intent(this, ViewGoalsActivity::class.java))
        }

        viewTotalsBtn.setOnClickListener {
            startActivity(Intent(this, ViewCategoryTotalsActivity::class.java))
        }

        // =========================
        // 📌 BUDGET OVERVIEW UI
        // =========================
        val tvRemaining = findViewById<TextView>(R.id.tvRemainingBudget)
        val progressBar = findViewById<ProgressBar>(R.id.budgetProgressBar)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {

            // 1. Monthly Budget Goal
            val totalGoal = db.goalDao().getMonthlyGoal() ?: 5000.0

            // 2. Total Spending
            val totalSpent = db.expenseDao().getTotalSpending() ?: 0.0

            val remaining = totalGoal - totalSpent

            // 3. Update UI safely
            tvRemaining.text = "R${String.format("%.2f", remaining)}"

            progressBar.max = totalGoal.toInt()
            progressBar.progress = totalSpent.toInt()

            // 4. Budget warning system
            if (totalSpent > totalGoal) {
                tvRemaining.setTextColor(Color.RED)
                Toast.makeText(
                    this@HomeActivity,
                    "Warning: Monthly Budget Exceeded!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}