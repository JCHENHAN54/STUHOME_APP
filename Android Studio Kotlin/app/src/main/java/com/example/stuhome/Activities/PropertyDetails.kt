package com.example.stuhome.Activities

import Global.UserGlobal
import Retrofit.APIRetrofit
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Booking
import model.GetProperty
import model.Property
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PropertyDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_details)

        //Variables de Property Details:
        val booking_btn = findViewById<AppCompatButton>(R.id.createProperty_btn)
        val backIcon = findViewById<ImageView>(R.id.back_icon);

        //Coger el objeto property.
        val property = intent.getSerializableExtra("PROPERTY_OBJECT") as GetProperty

        val property_name = findViewById<TextView>(R.id.name_property)
        val property_address =findViewById<TextView>(R.id.address_property)
        val property_description = findViewById<TextView>(R.id.description_property)
        val property_additional = findViewById<TextView>(R.id.additional_notes_property)
        val property_image = findViewById<ImageView>(R.id.image_property)
        val property_price = findViewById<TextView>(R.id.property_price)
        val property_user_name = findViewById<TextView>(R.id.name_user_property)
        val property_user_image = findViewById<ImageView>(R.id.image_user_property)

        //Switch
        val property_air = findViewById<Button>(R.id.air_property)
        val property_pet = findViewById<Button>(R.id.pet_property)
        val property_parking = findViewById<Button>(R.id.parking_property)
        val property_smoking = findViewById<Button>(R.id.smoking_property)
        val property_washer = findViewById<Button>(R.id.washer_property)
        val property_wifi = findViewById<Button>(R.id.wifi_property)

        property_air.visibility = View.GONE
        property_pet.visibility = View.GONE
        property_parking.visibility = View.GONE
        property_smoking.visibility = View.GONE
        property_washer.visibility = View.GONE
        property_wifi.visibility = View.GONE

        property_price.setText(property.property_price.toString()+"$/month")


        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }
        booking_btn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/booking/")
                    .addConverterFactory(
                        GsonConverterFactory.create()).client(client).build()
                var respuesta = conexion.create(APIRetrofit::class.java)
                    .ApiCreateBooking("createBooking", Booking(0,User(0,"","","",""
                        ,"","","",""), UserGlobal.UserLoginInfo.userEmail,property.property_name,property.property_description,property.property_price))
                withContext(Dispatchers.Main) {
                    if (respuesta.isSuccessful) {
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(applicationContext, "The Booking has been created!!", duration)
                        toast.show()
                    }
                }
            }
        }

        // Mostrar la información de la propiedad en la actividad
        property_name.setText(property.property_name)
        property_address.setText(property.property_address)
        property_description.setText(property.property_description)

        if(property.additional_notes.length == 0){
            property_additional.setText("Additional notes: There is no additional notes.")
        }else{
            property_additional.setText(property.additional_notes)
        }

        //Set image to property_image:
        if (property.image != null){
            val imageBytes = Base64.decode(property?.image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            property_image.setImageBitmap(bitmap)
        }

        //Comprobacion de los features:
        if(property.airConditioning == true){
            property_air.visibility = View.VISIBLE
        }
        if(property.petfriendly == true){
            property_pet.visibility = View.VISIBLE
        }
        if(property.parking == true){
            property_parking.visibility = View.VISIBLE
        }
        if(property.smoking == true){
            property_smoking.visibility = View.VISIBLE
        }
        if(property.washer == true){
            property_washer.visibility = View.VISIBLE
        }
        if(property.wifi == true){
            property_wifi.visibility = View.VISIBLE
        }

        //Coger la informacion del usuario dicho property.
        val intent = Intent(this, UserInfo::class.java)
        apiReadProperty(property,property_user_name,property_user_image,intent)
    }

    fun apiReadProperty(property: GetProperty, property_user_name: TextView, property_user_image: ImageView,intent: Intent){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadProperty("readProperty", Property(property.id,User(0,"","","",""
                ,"","","",""),"","","","","",null
                ,false,false,false,false,false,false,0,""))
            withContext(Dispatchers.Main) {
                if (respuesta.isSuccessful) {
                    var property = respuesta.body()
                    property_user_name.setText(property?.name)
                    if(property?.image != null){
                        val imageBytes = Base64.decode(property?.image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        property_user_image.setImageBitmap(bitmap)
                    }
                    property_user_image.setOnClickListener {
                        intent.putExtra("USER_EMAIL", property?.email)
                        startActivity(intent)
                    }
                }
            }
        }
    }



}

