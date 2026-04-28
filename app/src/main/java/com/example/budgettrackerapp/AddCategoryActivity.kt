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

        // ✅ Back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val categoryInput = findViewById<EditText>(R.id.categoryInput)
        val saveBtn = findViewById<Button>(R.id.saveCategoryBtn)

        // ✅ Initialize DB
        val db = AppDatabase.getDatabase(this)

        saveBtn.setOnClickListener {
            val categoryName = categoryInput.text.toString().trim()

            if (categoryName.isEmpty()) {
                Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT).show()
            } else {

                lifecycleScope.launch {
                    db.categoryDao().insert(Category(name = categoryName))

                    runOnUiThread {
                        Toast.makeText(this@AddCategoryActivity, "Category saved!", Toast.LENGTH_SHORT).show()
                        categoryInput.text.clear()
                        finish()
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}