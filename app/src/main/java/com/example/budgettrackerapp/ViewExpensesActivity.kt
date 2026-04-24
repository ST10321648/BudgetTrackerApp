package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        // Enables back navigation in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById<ListView>(R.id.expenseList)

        // ArrayAdapter is used to display the list of expenses in a simple list format
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            AddExpenseActivity.expensesList // Retrieves data stored from AddExpenseActivity
        )

        // Connect adapter to ListView
        listView.adapter = adapter
    }

    // Handles back button press in the action bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}