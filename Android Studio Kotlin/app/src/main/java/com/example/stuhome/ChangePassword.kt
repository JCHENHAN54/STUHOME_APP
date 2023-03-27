package com.example.stuhome

import Retrofit.APIRetrofit
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        //Variables: (Textfield, EditText)
        val backIcon = findViewById<ImageView>(R.id.back_icon);
        val changePassword = findViewById<AppCompatButton>(R.id.changePassBtn)
        val newPassEt = findViewById<EditText>(R.id.newPassEt)
        val userEmailEt = findViewById<EditText>(R.id.userEmailEt)

        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }
        changePassword.setOnClickListener(){
            if(newPassEt.text.isEmpty() || userEmailEt.text.isEmpty()){
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, "You have to complete all the fields", duration)
                toast.show()
            }else{
                val newPass:String  = newPassEt.text.toString();
                val userEmail:String = userEmailEt.text.toString();
                //Llamar la funcion de change password:
                apiChangePassword(userEmail,newPass)
            }
        }

    }

    fun apiChangePassword(userMail:String,newPass:String){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiChangePassword("changeUserPwd", User(0,newPass,"","",userMail,"","","",""));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Password changed successfully.", duration)
                    toast.show()
                    finish()
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "An error has ocurred.", duration)
                    toast.show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // No hacemos nada
    }

}