package com.example.stuhome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler;

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Espera 2 segundos, y inicia la applicacion.
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreen, Login::class.java)
            startActivity(intent)
        }, 2000)

    }
}