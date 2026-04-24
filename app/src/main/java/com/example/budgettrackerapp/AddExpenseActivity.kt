package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    // Shared list used to temporarily store expenses while the app is running
    // This avoids using a database at this stage
    companion object {
        val expensesList = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Enables the back button in the action bar for navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val amountInput = findViewById<EditText>(R.id.amountInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)

        saveBtn.setOnClickListener {

            val amount = amountInput.text.toString()
            val description = descriptionInput.text.toString()

            // Validate that the amount is not empty and is a valid number
            if (amount.isEmpty() || amount.toDoubleOrNull() == null) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()

                // Validate that description is not empty
            } else if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()

            } else {
                // Combine description and amount into a readable format
                val expense = "$description - R$amount"

                // Add the expense to the shared list
                expensesList.add(expense)

                // Notify the user that the expense was saved
                Toast.makeText(this, "Expense saved successfully!", Toast.LENGTH_SHORT).show()

                // Clear inputs for next entry
                amountInput.text.clear()
                descriptionInput.text.clear()
            }
        }
    }

    // Handles the action bar back button functionality
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}