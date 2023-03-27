package com.example.stuhome

import Global.UserGlobal
import Global.UserLoginInfo
import Retrofit.APIRetrofit
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.Fragments.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Property
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateProperty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_property)

        //Btn
        val backIcon = findViewById<ImageView>(R.id.back_icon);
        val createBtn = findViewById<AppCompatButton>(R.id.createProperty_btn)

        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }

        //Variables para crear properties.
        val PropertyImage = findViewById<AppCompatButton>(R.id.prepertyImages)
        val PropertyName = findViewById<EditText>(R.id.property_name)
        val ProperTyAddress = findViewById<EditText>(R.id.property_address)
        val ProperTyCity = findViewById<EditText>(R.id.property_city)
        val ProperTyDescription = findViewById<EditText>(R.id.property_description)
        val ProperTyPrice = findViewById<EditText>(R.id.property_price)
        val AdditionalNotes = findViewById<EditText>(R.id.additional_notes)

        //Objeto User para pasar el property

        //Boolean
        val airCondition = findViewById<Switch>(R.id.air_switch)
        val petFriendly = findViewById<Switch>(R.id.pet_swtich)
        val parking = findViewById<Switch>(R.id.parking_switch)
        val wifi = findViewById<Switch>(R.id.wifi_switch)
        val washer = findViewById<Switch>(R.id.washer_swtich)
        val smoking = findViewById<Switch>(R.id.smoking_switch)

        //Comprobaciones de si esta activado o no.

        //Comprobacion y validaciones del button para crear un propietario.
        createBtn.setOnClickListener {
//            if(PropertyName.text.isEmpty() || ProperTyAddress.text.isEmpty() || ProperTyCity.text.isEmpty() || ProperTyDescription.text.isEmpty()
//                || ProperTyPrice.text.isEmpty())
//            {
//                val duration = Toast.LENGTH_SHORT
//                val toast = Toast.makeText(applicationContext, "You have to complet all the fields.", duration)
//                toast.show()
//            }else{
       // }
                val PropertyName = PropertyName.text.toString()
                val PropertyAddress = ProperTyAddress.text.toString()
                val PropertyCity = ProperTyCity.text.toString()
                val PropertyDescription = ProperTyDescription.text.toString()
                val PropertyPrice = ProperTyPrice.text.toString()
                val AdditionalNotes = AdditionalNotes.text.toString()
                val UserEmailText = UserGlobal.UserLoginInfo.userEmail;
               ApiCreateProperty(PropertyName,PropertyAddress,PropertyCity,PropertyDescription, PropertyPrice,AdditionalNotes);
        }
    }

    fun ApiCreateProperty(propertyName:String, propertyAddress:String, propertyCity:String, propertyDescription:String,propertyPrice:String,
        additionalNotes:String){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiCreateProperty("createProperty",
                    Property(0,User(0,"","","","","","","",
                ""),UserGlobal.UserLoginInfo.userEmail,"","","","","",false,false,false,false
                        ,false,false,0,"de momento no hay nada.."));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Correct!!", duration)
                    toast.show()
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Incorrect!!", duration)
                    toast.show()
                }
            }
        }
    }

}