package com.example.stuhome.Activities

import Global.UserGlobal
import Global.UserProfileImg
import Global.UserPropertyImg
import Retrofit.APIRetrofit
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.stuhome.R
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class CreateProperty : AppCompatActivity() {

    private lateinit var PropertyImg: ImageView
    private lateinit var text: TextView
    private lateinit var imageBytes: ByteArray

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
        val PropertyName = findViewById<EditText>(R.id.property_name)
        val ProperTyAddress = findViewById<EditText>(R.id.property_address)
        val ProperTyCity = findViewById<EditText>(R.id.property_city)
        val ProperTyDescription = findViewById<EditText>(R.id.property_description)
        val ProperTyPrice = findViewById<EditText>(R.id.property_price)
        val AdditionalNotes = findViewById<EditText>(R.id.additional_notes)

        /* =============Configuration User property Image=============== */
        PropertyImg = findViewById(R.id.propertyImages)
        text = findViewById(R.id.image_info)

        PropertyImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 10)
        }

        //Boolean
        val airCondition = findViewById<Switch>(R.id.air_switch)
        val petFriendly = findViewById<Switch>(R.id.pet_swtich)
        val parking = findViewById<Switch>(R.id.parking_switch)
        val wifi = findViewById<Switch>(R.id.wifi_switch)
        val washer = findViewById<Switch>(R.id.washer_swtich)
        val smoking = findViewById<Switch>(R.id.smoking_switch)

        //Comprobacion y validaciones del button para crear un propietario.
        createBtn.setOnClickListener {
            if(PropertyName.text.isEmpty() || ProperTyAddress.text.isEmpty() || ProperTyCity.text.isEmpty() || ProperTyDescription.text.isEmpty()
                || ProperTyPrice.text.isEmpty())
            {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, "You have to complete all the required fields。", duration)
                toast.show()
            }else{
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.baseline_person_pin_24)
                val PropertyName = PropertyName.text.toString()
                val PropertyAddress = ProperTyAddress.text.toString()
                val PropertyCity = ProperTyCity.text.toString()
                val PropertyDescription = ProperTyDescription.text.toString()
                val PropertyPrice = ProperTyPrice.text.toString()
                val PropertyPriceInt = PropertyPrice.toInt()
                val AdditionalNotes = AdditionalNotes.text.toString()
                //Switch.
                var airConditionBool: Boolean = false
                var petFriendlyBool: Boolean = false
                var parkingBool: Boolean = false
                var wifiBool: Boolean = false
                var washerBool: Boolean = false
                var smokingBool: Boolean = false
                //Comprobaciones de si esta activado o no.
                if (airCondition.isChecked) {
                    airConditionBool = true
                }
                if (petFriendly.isChecked) {
                    petFriendlyBool = true
                }
                if (parking.isChecked) {
                    parkingBool = true
                }
                if (wifi.isChecked) {
                    wifiBool = true
                }
                if (washer.isChecked) {
                    washerBool = true
                }
                if (smoking.isChecked) {
                    smokingBool = true
                }
                //El tamaño del imagen no se puede estar mas grande que 3MB.
                if(imageBytes.size > 3_000_000){
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "The uploaded image cannot be larger than 3MB", duration)
                    toast.show()
                }else{
                    ApiCreateProperty(PropertyName,PropertyAddress,PropertyCity,PropertyDescription, PropertyPriceInt,AdditionalNotes,airConditionBool
                        ,petFriendlyBool,parkingBool,wifiBool,washerBool,smokingBool);
                }
        }
        }
    }

    fun ApiCreateProperty(propertyName:String, propertyAddress:String, propertyCity:String, propertyDescription:String,propertyPrice:Int,
        additionalNotes:String,airCondition:Boolean,petFriendly:Boolean,parking:Boolean,wifi:Boolean,washer:Boolean,smoking:Boolean){
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
                        ""),UserGlobal.UserLoginInfo.userEmail,propertyName,propertyAddress,propertyCity,
                        propertyDescription,imageBytes,airCondition,petFriendly,parking,wifi
                        ,washer,smoking,propertyPrice,additionalNotes));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Correct!!", duration)
                    toast.show()
                    finish() //Cerrar el activity.
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Incorrect!!", duration)
                    toast.show()
                }
            }
        }
    }

    //Guardar la Ruta dentro del objeto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            PropertyImg.setImageURI(selectedImage)
            text.setText("")

            //conventir el imagen seleccionado a un bytearray.
            val inputStream: InputStream? = selectedImage?.let { contentResolver.openInputStream(it) }
            val byteArrayOutputStream = ByteArrayOutputStream()
            if (inputStream != null) {
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, length)
                }
                inputStream.close()
            }
            imageBytes = byteArrayOutputStream.toByteArray()
        }
    }

}