package com.example.stuhome

import Retrofit.APIRetrofit
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
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


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //Variables de editText:
        val emailEt = findViewById<EditText>(R.id.emailET);
        val passEt = findViewById<EditText>(R.id.passwordET);

        //Variables de signin layout.
        val signUpTextview: TextView = findViewById(R.id.singUpBtn)
        val signinBtn: AppCompatButton = findViewById(R.id.signInBtn)

        //Funciones setOnListener:
        signUpTextview.setOnClickListener {
            val intent: Intent = Intent(this, SignUp::class.java)
            startActivity(intent);
        }

        //Funcion comprobacion de username y password:
        signinBtn.setOnClickListener {
            val intent: Intent = Intent(this, Home::class.java)
            //pasar valores de edittext a String.
            val email:String  = emailEt.text.toString();
            val password:String = passEt.text.toString();
            //Solo va iniciar cuando los campos de username y password no esta vacio.
            if (emailEt.text.isEmpty() || passEt.text.isEmpty()) {
                val text = "Enter your usernmae or password."
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }else{
               apiLogin(email,password,intent)
            }
        }

    }

    fun apiLogin(email:String,password:String,intent: Intent){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/users/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiLogin("login", User(0,password,"","",email,"","",""));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Welcome!!", duration)
                    toast.show()
                    intent.putExtra("email",email)
                    intent.putExtra("password",password)
                    startActivity(intent); //ir al Home Activity.
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "The email or password is incorrect", duration)
                    toast.show()
                }
            }
        }
    }

}







