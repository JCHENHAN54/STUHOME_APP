package com.example.stuhome

import Retrofit.APIRetrofit
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.Fragments.ProfileFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //EditText de la pantalla edit profile.
        val EditNameEt = findViewById<EditText>(R.id.edit_name)
        val EditSurnameEt = findViewById<EditText>(R.id.edit_surname)
        val EditDescriptionEt = findViewById<EditText>(R.id.edit_description)
        val EditDirectionEt = findViewById<EditText>(R.id.edit_direccion)
        val EditStudiesEt = findViewById<EditText>(R.id.edit_studies)

        //Buttones de edit profile.
        val saveChangesBtn: AppCompatButton = findViewById(R.id.profile_save_changesBtn)
       // val changeProfileImgBtn: AppCompatButton = findViewById(R.id.changeProfileimg)

        saveChangesBtn.setOnClickListener {
            //Pasar edittext a string:
            val EditName:String = EditNameEt.text.toString();
            val EditSurname:String = EditSurnameEt.text.toString();
            val EditDescription:String  = EditDescriptionEt.text.toString();
            val EditDirection:String = EditDirectionEt.text.toString();
            val EditStudies:String = EditStudiesEt.text.toString();

            //Pasar el username y el password que hemos introducido en Activity Login:
            val loginEmail = intent.getStringExtra("loginEmail")
            //Debe rellenar todos los campos:
            if(EditName.isEmpty() || EditSurname.isEmpty() ||
                EditDescription.isEmpty() || EditDirection.isEmpty() || EditStudies.isEmpty()){
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, "You have to complete all the fields.", duration)
                toast.show()
            }else{
                val intent = Intent(this, Home::class.java)
               apiEditProfile(loginEmail.toString(),EditName,EditSurname,EditDescription,EditDirection,EditStudies,intent)
            }
        }

    }

    //Funcion Retrofit para crear usuarios nuevos.
    fun apiEditProfile(loginEmail:String,EditName:String,
                       EditSurname:String,EditDescription:String,EditDirection:String,EditStudies:String,intent: Intent){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/users/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiEditProfile("profile", User(0,"",EditName,EditSurname,loginEmail,EditDescription,EditStudies,EditDirection));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Profile changed successfully.", duration)
                    toast.show()
                   startActivity(intent);
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "An error has occurred", duration)
                    toast.show()
                }
            }
        }
    }

}