package com.example.stuhome.Activities

import PropertyListAdapter
import Retrofit.APIRetrofit
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.GetProperty
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val backIcon = findViewById<ImageView>(R.id.back_icon);
        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }

        //Coger infomacion del usuario.
        var user_mail = intent.getStringExtra("USER_EMAIL")
        //Lista de porperties:
        val propertyList = mutableListOf<GetProperty>();

        //Variable de layout User info.
        val userinfo_img = findViewById<ImageView>(R.id.userinfo_img)
        val userinfo_username = findViewById<TextView>(R.id.userinfo_username)
        val userinfo_email = findViewById<TextView>(R.id.userinfo_email)
        val text_dismiss = findViewById<TextView>(R.id.text_dismiss)


        //Api para leer la lista de properties.
        apiUserInfoPropertyList(propertyList,user_mail.toString(),text_dismiss)
        //Api para leer informacion del usuario.
        apiReadUserInfo(user_mail.toString(),userinfo_img,userinfo_username,userinfo_email)
    }

    //Funcion para leer informacion de usuario.
    fun apiReadUserInfo(usermail: String,userinfo_img: ImageView,userinfo_username:TextView,userinfo_email:TextView){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadUser("GProfile",User(0,"","","",usermail,"","","",""))
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val user = respuesta.body()
                    if(user?.image != null){
                        val imageBytes = Base64.decode(user?.image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        userinfo_img.setImageBitmap(bitmap)
                    }
                    userinfo_username.setText(user?.name)
                    userinfo_email.setText(user?.email)
                }
            }
        }
    }


    //Funcion para leer propietarios.
    fun apiUserInfoPropertyList(propertyList: MutableList<GetProperty>,usermail: String,text_dismiss:TextView){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadMyProperty("readMyProperties",User(0,"","","",usermail,"","","",""))
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val properties = respuesta.body()
                    if(properties != null){
                        propertyList.addAll(properties)
                        if(propertyList.isEmpty()){
                            text_dismiss.setText("There are no properties...")
                            text_dismiss.visibility = View.VISIBLE
                        }else{
                            text_dismiss.visibility = View.GONE
                            //Configuracion RecyclerView.
                            val recyclerView = findViewById<RecyclerView>(R.id.listUserInfoProperty);
                            //recyclerView.layoutManager = LinearLayoutManager(context);
                            recyclerView?.layoutManager = GridLayoutManager(applicationContext,2);
                            recyclerView?.adapter = PropertyListAdapter(propertyList);
                        }
                    }
                }
            }
        }
    }

}