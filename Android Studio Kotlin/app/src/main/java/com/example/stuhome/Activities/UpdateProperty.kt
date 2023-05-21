package com.example.stuhome.Activities

import Retrofit.APIRetrofit
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.GetProperty
import model.Property
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.InputStream

class UpdateProperty : AppCompatActivity() {

    private lateinit var PropertyImg: ImageView
    private lateinit var imageBytes: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_property)

        //Btn
        val backIcon = findViewById<ImageView>(R.id.back_icon);
        val editBtn = findViewById<AppCompatButton>(R.id.editProperty_btn)


        //Coger el objeto Property pasado.
        val property = intent.getSerializableExtra("PROPERTY_OBJECT") as GetProperty

        //Variables para crear properties.
        val PropertyName = findViewById<EditText>(R.id.up_property_name)
        val ProperTyAddress = findViewById<EditText>(R.id.up_property_address)
        val ProperTyCity = findViewById<EditText>(R.id.up_property_city)
        val ProperTyDescription = findViewById<EditText>(R.id.up_property_description)
        val ProperTyPrice = findViewById<EditText>(R.id.up_property_price)
        val AdditionalNotes = findViewById<EditText>(R.id.up_additional_notes)

        /* =============Configuration User property Image=============== */
        PropertyImg = findViewById(R.id.up_propertyImages)
        imageBytes=byteArrayOf()

        PropertyImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 10)
        }

        //Boolean
        val airCondition = findViewById<Switch>(R.id.up_air_switch)
        val petFriendly = findViewById<Switch>(R.id.up_pet_swtich)
        val parking = findViewById<Switch>(R.id.up_parking_switch)
        val wifi = findViewById<Switch>(R.id.up_wifi_switch)
        val washer = findViewById<Switch>(R.id.up_washer_swtich)
        val smoking = findViewById<Switch>(R.id.up_smoking_switch)

        //Mostrar informacion de Property por defecto:
        ApiGetEditProperty(PropertyImg,PropertyName,ProperTyAddress,ProperTyCity,ProperTyDescription,ProperTyPrice,AdditionalNotes,airCondition,
            petFriendly,parking,wifi,washer,smoking, property)

        /* ===================Condiguracion Modificacion Property============================= */
        //Comprobacion y validaciones del button para crear un propietario.
        editBtn.setOnClickListener {
            if(PropertyName.text.isEmpty() || ProperTyAddress.text.isEmpty() || ProperTyCity.text.isEmpty() || ProperTyDescription.text.isEmpty()
                || ProperTyPrice.text.isEmpty() || PropertyImg == null)
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
                    ApiUpdateProperty(PropertyName,PropertyAddress,PropertyCity,PropertyDescription,PropertyPriceInt,AdditionalNotes
                        ,airConditionBool,petFriendlyBool,parkingBool,wifiBool,washerBool,smokingBool,property);
                }
            }
        }

    }

    //Method Get edti property info:
    fun ApiGetEditProperty(PropertyImg: ImageView, Propertyname: EditText, PropertyAddress: EditText, Propertycity: EditText, PropertyDescription: EditText,
                           PropertyPrice: EditText, AdditionalNotes: EditText, airCondition: Switch, petFriendly: Switch, parking: Switch, wifi: Switch, washer: Switch, smoking: Switch, property: GetProperty
    ){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiGetEditProperty("updateGetProperty", Property(property.id, User(0,"","","",""
                    ,"","","",""),"","","","","",null
                    ,false,false,false,false,false,false,0,"")
                )
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    var property = respuesta.body()
                    Propertyname.setText(property?.property_name)
                    PropertyAddress.setText(property?.property_address)
                    Propertycity.setText(property?.property_city)
                    PropertyDescription.setText(property?.property_description)
                    PropertyPrice.setText(property?.property_price.toString())
                    AdditionalNotes.setText(property?.additional_notes)

                    //Comprobacion de los features:
                    if(property?.airConditioning == true){
                        airCondition.isChecked = true
                    }
                    if(property?.petfriendly == true){
                        petFriendly.isChecked = true
                    }
                    if(property?.parking == true){
                        parking.isChecked = true
                    }
                    if(property?.smoking == true){
                        smoking.isChecked = true
                    }
                    if(property?.washer == true){
                        washer.isChecked = true
                    }
                    if(property?.wifi == true){
                        wifi.isChecked = true
                    }

                    if(property?.image != null){
                        val imageBytes = Base64.decode(property?.image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        PropertyImg.setImageBitmap(bitmap)
                    }
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Error read property", duration)
                    toast.show()
                }
            }
        }
    }

    fun ApiUpdateProperty(Propertyname:String,PropertyAddress:String,Propertycity:String,PropertyDescription:String,
                          PropertyPrice:Int,AdditionalNotes:String,airCondition:Boolean,petFriendly:Boolean,parking:Boolean,wifi:Boolean,washer:Boolean,smoking:Boolean,property: GetProperty
    ){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiUpdateProperty("updateProperty", Property(property.id,
                    User(0,"","","",""
                    ,"","","",""),"",Propertyname,PropertyAddress,Propertycity,PropertyDescription,imageBytes
                    ,airCondition,petFriendly,parking,wifi,washer,smoking,PropertyPrice,AdditionalNotes)
                );
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Property changed successfully!!", duration)
                    toast.show()
                    finish() //Cerrar el activity.
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Cannot change this property.", duration)
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