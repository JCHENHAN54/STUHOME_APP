package com.example.stuhome.Activities

import Global.UserGlobal
import Global.UserProfileImg
import Retrofit.APIRetrofit
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.EditText
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

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //BUtton de Sign_up layout
        val createAccountBtn:AppCompatButton = findViewById(R.id.createAccountBtn)
        val registerSigninBtn:TextView = findViewById(R.id.register_singinBtn)

        //Variables EditText de user:
        val nameEditText = findViewById<EditText>(R.id.register_name)
        val surnameEditText = findViewById<EditText>(R.id.register_surname)
        val studiesEditText = findViewById<EditText>(R.id.register_studies)
        val mailEditText = findViewById<EditText>(R.id.register_mail)
        val directionEditText = findViewById<EditText>(R.id.register_direcction)
        val descriptionEditText = findViewById<EditText>(R.id.register_description)
        val passwordEditText = findViewById<EditText>(R.id.register_password)

        //clicamos el Button create Account.
        createAccountBtn.setOnClickListener {
            val name: String = nameEditText.text.toString()
            val surname: String = surnameEditText.text.toString()
            val studies: String = studiesEditText.text.toString()
            val mail: String = mailEditText.text.toString()
            val direction: String = directionEditText.text.toString()
            val description: String = descriptionEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            val intent: Intent = Intent(this, Login::class.java)

            var isValid = true

            if (surname.isEmpty() || mail.isEmpty() || name.isEmpty() ||
                password.isEmpty() || studies.isEmpty() || direction.isEmpty() || description.isEmpty()
            ) {
                val text = "You have to complete all fields."
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
                isValid = false
            }

            if (password.length < 6) {
                val text = "The password must have more than 6 characters."
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
                isValid = false
            }

            val emailPattern = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
            if (!mail.matches(emailPattern)) {
                val text = "Invalid email format."
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
                isValid = false
            }

            if (isValid) {
                apiRegister(name, surname, studies, mail, direction, description, password, intent)
            }
        }

        //clicamos el Button Signin:
        registerSigninBtn.setOnClickListener{
            val intent: Intent = Intent(this, Login::class.java);
            startActivity(intent)
        }
    }

    //Funcion Retrofit para crear usuarios nuevos.
    fun apiRegister(name:String,surname:String,studies:String,mail:String,direction: String,description:String,password:String,intent: Intent){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiRegister("signin", User(0,password,name,surname,mail,description,studies,direction,
                    ""));
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Account created!!", duration)
                    toast.show()
                    startActivity(intent);
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "This email already exists", duration)
                    toast.show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // No hacemos nada
    }

}
