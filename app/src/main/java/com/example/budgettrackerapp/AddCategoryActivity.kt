package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        // Enables back navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val categoryInput = findViewById<EditText>(R.id.categoryInput)
        val saveBtn = findViewById<Button>(R.id.saveCategoryBtn)

        saveBtn.setOnClickListener {
            val text = categoryInput.text.toString()

            // Check if the user entered a category
            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT).show()
            } else {
                // Display confirmation message
                Toast.makeText(this, "Category added successfully!", Toast.LENGTH_SHORT).show()

                // Clear input field after saving
                categoryInput.text.clear()
            }
        }
    }

    // Handles back button functionality
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}