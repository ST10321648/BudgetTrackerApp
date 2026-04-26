package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// ✅ Room + Coroutines
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Category

class AddCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        // Enables back navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val categoryInput = findViewById<EditText>(R.id.categoryInput)
        val saveBtn = findViewById<Button>(R.id.saveCategoryBtn)

        // ✅ Initialize database
        val db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {
            val text = categoryInput.text.toString().trim()

            // Validate input
            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT).show()
            } else {

                // ✅ Save to RoomDB
                lifecycleScope.launch {
                    db.categoryDao().insert(Category(name = text))
                }

                // Feedback to user
                Toast.makeText(this, "Category added successfully!", Toast.LENGTH_SHORT).show()

                // Clear input
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