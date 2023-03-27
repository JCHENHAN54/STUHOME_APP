package com.example.stuhome

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val backIcon = findViewById<ImageView>(R.id.back_icon);
        backIcon.setOnClickListener{
            finish()
        }
    }
    override fun onBackPressed() {
        // No hacemos nada
    }
}