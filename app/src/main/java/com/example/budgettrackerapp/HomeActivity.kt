package com.example.budgettrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val addCategoryBtn = findViewById<Button>(R.id.addCategoryBtn)
        val addExpenseBtn = findViewById<Button>(R.id.addExpenseBtn)

        addCategoryBtn.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }

        addExpenseBtn.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }
}