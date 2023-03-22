package com.example.stuhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MyProperty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_property)
        //Variables: (Textfield, EditText)
        val backIcon = findViewById<ImageView>(R.id.back_icon);

        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }
    }
}