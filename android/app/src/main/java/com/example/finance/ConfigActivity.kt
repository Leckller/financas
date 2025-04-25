package com.example.finance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import androidx.core.net.toUri

class ConfigActivity : AppCompatActivity(), View.OnClickListener {

    val configText: TextView by lazy {findViewById(R.id.config_text)}
    val telBtn: Button by lazy { findViewById(R.id.config_tel) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        configText.text = intent.getStringExtra("text")

        telBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.config_tel -> {
                val it = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE, "MT FODA")
                    putExtra(AlarmClock.EXTRA_HOUR, 2)
                    putExtra(AlarmClock.EXTRA_MINUTES, 30)
                }
                if(it.resolveActivity(packageManager) != null) {
                    startActivity(it)
                }
            }

        }

    }
}