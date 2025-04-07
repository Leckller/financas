package com.example.finance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.data.Adapters.TransactionAdapter
import com.example.finance.data.Transaction

class MainActivity : AppCompatActivity() {
    val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    val button: Button by lazy { findViewById(R.id.header_profile) }

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

        button.setOnClickListener{
            //Criando o intent para nossa MainActivity2
            val intent = Intent(this, Profile::class.java)
            //iniciando a Activity
            startActivity(intent)
        }

    }

}