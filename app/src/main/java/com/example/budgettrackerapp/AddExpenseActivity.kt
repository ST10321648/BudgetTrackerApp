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

    // 🔹 Store selected image URI
    private var selectedImageUri: String? = null

    // 🔹 ImageView reference
    private lateinit var imageView: ImageView

    // 🔹 Image picker launcher (OUTSIDE onCreate)
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

        // 🔹 UI elements
        val amountInput = findViewById<EditText>(R.id.amountInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)
        val uploadBtn = findViewById<Button>(R.id.uploadImageBtn)
        imageView = findViewById(R.id.expenseImageView)

        // 🔹 Database instance
        val db = AppDatabase.getDatabase(this)

        // 📸 Open gallery when button clicked
        uploadBtn.setOnClickListener {
            pickImage.launch("image/*")
        }

        // 💾 Save expense
        saveBtn.setOnClickListener {

            val amount = amountInput.text.toString()
            val description = descriptionInput.text.toString()

            if (amount.isEmpty() || amount.toDoubleOrNull() == null) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()

            } else if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()

            } else {

                val amountValue = amount.toDouble()

                lifecycleScope.launch {
                    db.expenseDao().insert(
                        Expense(
                            amount = amountValue,
                            description = description,
                            categoryId = 1, // 🔥 TEMP (we’ll fix with spinner later)
                            imageUri = selectedImageUri
                        )
                    )
                }

                Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show()

                // 🔄 Clear inputs
                amountInput.text.clear()
                descriptionInput.text.clear()
                imageView.setImageResource(android.R.drawable.ic_menu_gallery)
                selectedImageUri = null
            }
        }
    }

    // 🔙 Back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}