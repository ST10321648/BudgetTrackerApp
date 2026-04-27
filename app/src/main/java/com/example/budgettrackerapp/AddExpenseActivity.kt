package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.budgettrackerapp.data.local.Database.AppDatabase
import com.example.budgettrackerapp.data.local.entity.Expense

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val amountInput = findViewById<EditText>(R.id.amountInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)

        val db = AppDatabase.getDatabase(this)

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
                            categoryId = 1 // TEMP: assumes category exists
                        )
                    )
                }

                Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show()

                amountInput.text.clear()
                descriptionInput.text.clear()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}