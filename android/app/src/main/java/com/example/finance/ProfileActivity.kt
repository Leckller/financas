package com.example.finance

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity: AppCompatActivity() {

    val textTest: TextView by lazy { findViewById(R.id.intentTest) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val text = intent.getStringExtra("text");
        textTest.text = text.toString();
    }

}