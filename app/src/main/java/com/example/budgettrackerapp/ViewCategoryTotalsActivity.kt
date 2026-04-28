package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import kotlinx.coroutines.launch

class ViewCategoryTotalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_category_totals)

        // UI elements
        val totalsTextView = findViewById<TextView>(R.id.totalsTextView)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // Back button action
        backBtn.setOnClickListener {
            finish() // returns to previous screen
        }

        // Database
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            try {
                val totals = db.expenseDao().getCategoryTotals()

                if (totals.isNullOrEmpty()) {
                    totalsTextView.text = "No expenses found."
                } else {
                    val result = StringBuilder()

                    for (item in totals) {
                        result.append("${item.categoryName}: R${String.format("%.2f", item.total)}\n")
                    }

                    totalsTextView.text = result.toString()
                }

            } catch (e: Exception) {
                totalsTextView.text = "Error loading totals"
                e.printStackTrace()
            }
        }
    }
}