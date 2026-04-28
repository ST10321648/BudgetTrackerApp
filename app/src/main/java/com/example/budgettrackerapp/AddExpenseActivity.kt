package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.activity.result.contract.ActivityResultContracts
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Expense

class AddExpenseActivity : AppCompatActivity() {

    // 📸 Store selected image URI
    private var selectedImageUri: String? = null
    private lateinit var imageView: ImageView

    // 📸 Image picker
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

        // ✅ UI Elements (MAKE SURE XML MATCHES THESE IDS)
        val amountInput = findViewById<EditText>(R.id.amountInput)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)

        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)
        val uploadBtn = findViewById<Button>(R.id.uploadImageBtn)
        imageView = findViewById(R.id.expenseImageView)

        // ✅ DB
        val db = AppDatabase.getDatabase(this)

        // 📸 Open gallery
        uploadBtn.setOnClickListener {
            pickImage.launch("image/*")
        }

        // 💾 Save expense
        saveBtn.setOnClickListener {

            val amountStr = amountInput.text.toString().trim()
            val date = dateInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()

            val amount = amountStr.toDoubleOrNull()

            // ✅ Validation
            when {
                amount == null -> {
                    Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                }
                date.isEmpty() -> {
                    Toast.makeText(this, "Please enter a date", Toast.LENGTH_SHORT).show()
                }
                description.isEmpty() -> {
                    Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    lifecycleScope.launch {
                        db.expenseDao().insert(
                            Expense(
                                amount = amount,
                                date = date,
                                description = description,
                                categoryId = 1, // 🔥 TEMP: replace with spinner later
                                imageUri = selectedImageUri
                            )
                        )

                        Toast.makeText(this@AddExpenseActivity, "Expense saved!", Toast.LENGTH_SHORT).show()

                        // 🔄 Clear UI
                        amountInput.text.clear()
                        dateInput.text.clear()
                        descriptionInput.text.clear()
                        imageView.setImageResource(android.R.drawable.ic_menu_gallery)
                        selectedImageUri = null

                        finish()
                    }
                }
            }
        }
    }

    // 🔙 Back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}