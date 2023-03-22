package com.example.stuhome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.Fragments.HomeFragment

class CreateProperty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_property)

        //Btn
        val backIcon = findViewById<ImageView>(R.id.back_icon);
        val nextBtn = findViewById<AppCompatButton>(R.id.next_btn);

        //EditText
        val propertyName = findViewById<EditText>(R.id.property_name);
        val propertyAddress = findViewById<EditText>(R.id.property_address);
        val propertyCity = findViewById<EditText>(R.id.property_city);
        val propertyDescription = findViewById<EditText>(R.id.property_description);
        val propertyPrice = findViewById<EditText>(R.id.property_price);

        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }
        nextBtn.setOnClickListener(){
            if(propertyName.text.isEmpty() || propertyAddress.text.isEmpty() || propertyCity.text.isEmpty() || propertyDescription.text.isEmpty() || propertyPrice.text.isEmpty()){
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, "You must complete the required fields.", duration)
                toast.show()
            }else{
                apiCreateProperty();
            }
        }
    }

    fun apiCreateProperty(){
        finish();
    }

}