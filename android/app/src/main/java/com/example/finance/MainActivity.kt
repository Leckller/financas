package com.example.finance

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finance.data.Adapters.TransactionAdapter
import com.example.finance.data.Transaction

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    val profileBtn: Button by lazy { findViewById(R.id.header_profile) }
    val configButton: Button by lazy { findViewById(R.id.header_config) }

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

        profileBtn.setOnClickListener(this)
        configButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.header_config -> {
                val intent = Intent(this, ConfigActivity::class.java).apply {
                    putExtra("text", "hahaha, isso é bem legal")
                }
                startActivity(intent)
            }

            R.id.header_profile -> {
                val intent = Intent(this, ProfileActivity::class.java).apply {
                    putExtra("text", "Achei interessante.")
                }
                startActivity(intent)
            }

        }

    }

}