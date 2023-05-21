package com.example.stuhome.Activities

import Retrofit.APIRetrofit
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.GetProperty
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeleteProperty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_property)

        val backIcon = findViewById<ImageView>(R.id.back_icon);
        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }

        // Button eleminar
        val detelePropertyButton = findViewById<AppCompatButton>(R.id.deletePropertyBtn)
        val property = intent.getSerializableExtra("PROPERTY_OBJECT") as GetProperty

        detelePropertyButton.setOnClickListener {
            //Codigo Retrofit:
            CoroutineScope(Dispatchers.IO).launch {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                    .addConverterFactory(
                        GsonConverterFactory.create()).client(client).build()
                var respuesta = conexion.create(APIRetrofit::class.java)
                    .ApiDeleteProperty("deleteProperty/${property.id}")
                withContext(Dispatchers.Main) {
                    if (respuesta.isSuccessful) {
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(applicationContext, "Property deleted successfully!", duration)
                        toast.show()
                        val intent: Intent = Intent(this@DeleteProperty, MyProperty::class.java)
                        startActivity(intent);
                    }else{
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(applicationContext, "This property has already been deleted", duration)
                        toast.show()
                    }
                }
            }
        }

    }
}