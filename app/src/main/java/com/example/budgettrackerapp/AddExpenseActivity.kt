package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val amountInput = findViewById<EditText>(R.id.amountInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveBtn = findViewById<Button>(R.id.saveExpenseBtn)

        saveBtn.setOnClickListener {

            val amount = amountInput.text.toString()
            val description = descriptionInput.text.toString()

            if (amount.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            } else if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show()
                amountInput.text.clear()
                descriptionInput.text.clear()
            }
        }
    }
}