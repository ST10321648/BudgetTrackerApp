package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.budgettrackerapp.data.local.Database.AppDatabase

class ViewCategoryTotalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 Connect layout
        setContentView(R.layout.activity_view_category_totals)

        // 🔹 Connect TextView
        val totalsTextView = findViewById<TextView>(R.id.totalsTextView)

        // 🔹 Get database instance
        val db = AppDatabase.getDatabase(this)

        // 🔹 Fetch and display totals
        lifecycleScope.launch {
            try {
                val totals = db.expenseDao().getCategoryTotals()

                if (totals.isEmpty()) {
                    totalsTextView.text = "No expenses found."
                } else {
                    val result = StringBuilder()

                    for (item in totals) {
                        result.append("${item.categoryName}: R${item.total}\n")
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