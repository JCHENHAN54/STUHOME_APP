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

        //Control de social Links:
        val linkedinChen = findViewById<ImageView>(R.id.linkedin_chen)
        val linkedinPau = findViewById<ImageView>(R.id.linkedin_pau)
        val linkedinLhuis = findViewById<ImageView>(R.id.linkedin_lhuis)

        linkedinChen.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/jiahan-chen-1211731a0"))
            startActivity(intent)
        }
        linkedinPau.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/pau-guardia-vies-02172b239"))
            startActivity(intent)
        }
        linkedinLhuis.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/lhuis-enrique-marcelo-sotelo-241000147"))
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        // No hacemos nada
    }
}