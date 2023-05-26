package com.example.stuhome.Activities

import Global.UserGlobal
import Global.UserLoginInfo
import Global.UserProfileImg
import Retrofit.APIRetrofit
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*


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

        //Button show y hide password:
        val showPassword :ImageView = findViewById(R.id.passwordIcon)

        showPassword.setOnClickListener {
            // Obtener el modo de visibilidad actual del EditText
            val passwordVisibility = passEt.inputType
            // Cambiar el modo de visibilidad de la contraseña
            if (passwordVisibility == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                passEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPassword.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
            } else {
                passEt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPassword.setImageResource(R.drawable.close_eye)
            }
            // Mover el cursor al final del texto
            passEt.setSelection(passEt.text.length)
        }

        //Funciones setOnListener:
        signUpTextview.setOnClickListener {
            val intent: Intent = Intent(this, SignUp::class.java)
            startActivity(intent);
        }

        //Funcion comprobacion de username y password:
        signinBtn.setOnClickListener {
            val intent: Intent = Intent(this, ButtomBar::class.java)
            //pasar valores de edittext a String.
            val email:String  = emailEt.text.toString();
            val password:String = passEt.text.toString();
            //Inicializar el valor de UserProfileImg
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
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiLogin("login", User(0,password,"","",email,"","","",""));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    UserGlobal.UserLoginInfo = UserLoginInfo(email,password) //Guardar datos a Objeto Global.
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Welcome!!", duration)
                    toast.show()
                    startActivity(intent); //ir al Home Activity.
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "The email or password is incorrect", duration)
                    toast.show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // No hacemos nada
    }

}







