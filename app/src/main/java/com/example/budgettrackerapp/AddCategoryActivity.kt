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

        val categoryInput = findViewById<EditText>(R.id.categoryInput)
        val saveBtn = findViewById<Button>(R.id.saveCategoryBtn)

        saveBtn.setOnClickListener {
            val categoryText = categoryInput.text.toString()

            if (categoryText.isEmpty()) {
                Toast.makeText(this, "Please enter a category", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Category saved!", Toast.LENGTH_SHORT).show()
                categoryInput.text.clear()
            }
        }
    }
}