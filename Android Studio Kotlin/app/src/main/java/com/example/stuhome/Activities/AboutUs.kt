package com.example.stuhome.Activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.stuhome.R


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