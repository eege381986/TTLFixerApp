package com.example.ttlfixer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ttlManager: TtlManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ttlManager = TtlManager(this)

        val ttlInput = findViewById<EditText>(R.id.ttlInput)
        val applyBtn = findViewById<Button>(R.id.applyButton)

        ttlInput.setText(ttlManager.ttl.toString())

        applyBtn.setOnClickListener {
            val value = ttlInput.text.toString().toIntOrNull()
            if (value == null || value !in 1..255) {
                Toast.makeText(this, "TTL değeri 1-255 arası olmalı", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            ttlManager.ttl = value
            val ok = ttlManager.applyTtl()
            Toast.makeText(this, if (ok) "Uygulandı" else "Başarısız (root?)", Toast.LENGTH_SHORT).show()
        }
    }
}
