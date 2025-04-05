package com.example.finance

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.data.Adapters.TransactionAdapter
import com.example.finance.data.Transaction

class MainActivity : AppCompatActivity() {
    val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }

    val transactionList = listOf<Transaction>(
        Transaction("despesa", 5.28, 1),
        Transaction("despesa", 20.00, 2),
        Transaction("despesa", 138.00, 3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TransactionAdapter(transactionList)

    }

}