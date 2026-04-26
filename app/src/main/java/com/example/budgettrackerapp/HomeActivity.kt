package com.example.budgettrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Category

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ✅ DATABASE CONNECTION
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            db.categoryDao().insert(Category(name = "Food"))
        }

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
    }
}