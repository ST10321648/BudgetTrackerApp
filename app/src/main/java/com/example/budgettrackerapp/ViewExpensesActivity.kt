package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import kotlinx.coroutines.launch

class ViewExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById<ListView>(R.id.expenseList)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val expenses = db.expenseDao().getAll()

            val adapter = ArrayAdapter(
                this@ViewExpensesActivity,
                android.R.layout.simple_list_item_1,
                expenses.map { "${it.description} - R${it.amount}" }
            )

            listView.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}