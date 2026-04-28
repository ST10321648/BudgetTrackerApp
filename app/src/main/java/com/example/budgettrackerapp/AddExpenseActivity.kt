package com.example.budgettrackerapp

// Code Attribution
// Title: Add Category Feature using Room Database (Android)
// Author: Google Developers (Android Jetpack Team)
// Date: 2024
// Version: Android Jetpack (Room + Lifecycle + Coroutines)
// Available at: https://developer.android.com/training/data-storage/room
// Accessed: April 2026

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.activity.result.contract.ActivityResultContracts
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Expense
import com.example.budgettrackerapp.data.local.entity.Category
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private var selectedImageUri: String? = null
    private lateinit var imageView: ImageView
    private lateinit var categorySpinner: Spinner
    private var categoriesList: List<Category> = emptyList()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imageView.setImageURI(uri)
            selectedImageUri = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val amountInput = findViewById<EditText>(R.id.amountInput)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)
        val uploadBtn = findViewById<Button>(R.id.uploadImageBtn)
        categorySpinner = findViewById(R.id.categorySpinner)
        imageView = findViewById(R.id.expenseImageView)

        // Set current date as default
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateInput.setText(sdf.format(Date()))

        val db = AppDatabase.getDatabase(this)

        // Load Categories
        lifecycleScope.launch {
            categoriesList = db.categoryDao().getAll()
            if (categoriesList.isEmpty()) {
                Toast.makeText(this@AddExpenseActivity, "Please add a category first!", Toast.LENGTH_LONG).show()
            }
            val adapter = ArrayAdapter(
                this@AddExpenseActivity,
                android.R.layout.simple_spinner_item,
                categoriesList.map { it.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        uploadBtn.setOnClickListener {
            pickImage.launch("image/*")
        }

        saveBtn.setOnClickListener {
            val amountStr = amountInput.text.toString().trim()
            val dateStr = dateInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val amount = amountStr.toDoubleOrNull()

            val selectedCategoryIndex = categorySpinner.selectedItemPosition
            
            val dateMillis = try {
                sdf.parse(dateStr)?.time ?: System.currentTimeMillis()
            } catch (e: Exception) {
                System.currentTimeMillis()
            }

            when {
                categoriesList.isEmpty() -> Toast.makeText(this, "Add a category first", Toast.LENGTH_SHORT).show()
                amount == null -> Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                description.isEmpty() -> Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
                selectedCategoryIndex == AdapterView.INVALID_POSITION -> Toast.makeText(this, "Select a category", Toast.LENGTH_SHORT).show()
                else -> {
                    val categoryId = categoriesList[selectedCategoryIndex].categoryId
                    lifecycleScope.launch {
                        try {
                            db.expenseDao().insert(
                                Expense(
                                    amount = amount,
                                    date = dateMillis,
                                    description = description,
                                    categoryId = categoryId,
                                    imageUri = selectedImageUri
                                )
                            )

                            runOnUiThread {
                                Toast.makeText(this@AddExpenseActivity, "Expense saved!", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } catch (e: Exception) {
                            runOnUiThread {
                                Toast.makeText(this@AddExpenseActivity, "Error saving: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
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
