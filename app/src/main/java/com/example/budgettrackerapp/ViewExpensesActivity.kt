package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import kotlinx.coroutines.launch

class ViewExpensesActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        listView = findViewById(R.id.expenseList)
        db = AppDatabase.getDatabase(this)

        // ✅ BACK BUTTON (SAFE METHOD)
        val backBtn = findViewById<Button>(R.id.backBtn)

        backBtn.setOnClickListener {
            finish()
        }

        // 🔥 Load all expenses by default
        loadAllExpenses()

        // OPTIONAL FILTER
        val startDate = intent.getLongExtra("startDate", -1)
        val endDate = intent.getLongExtra("endDate", -1)

        if (startDate != -1L && endDate != -1L) {
            loadFilteredExpenses(startDate, endDate)
        }
    }

    // =========================
    // 📌 LOAD ALL EXPENSES
    // =========================
    private fun loadAllExpenses() {
        lifecycleScope.launch {
            val expenses = db.expenseDao().getAll()
            updateList(expenses.map { "${it.description} - R${it.amount}" })
        }
    }

    // =========================
    // 📌 FILTERED EXPENSES
    // =========================
    private fun loadFilteredExpenses(startDate: Long, endDate: Long) {
        lifecycleScope.launch {
            val expenses = db.expenseDao().getExpensesByDate(startDate, endDate)

            if (expenses.isEmpty()) {
                Toast.makeText(
                    this@ViewExpensesActivity,
                    "No expenses in this range",
                    Toast.LENGTH_SHORT
                ).show()
            }

            updateList(expenses.map { "${it.description} - R${it.amount}" })
        }
    }

    // =========================
    // 📌 UPDATE UI
    // =========================
    private fun updateList(items: List<String>) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        listView.adapter = adapter
    }
}