package com.example.stuhome.Activities

import Global.UserGlobal
import Retrofit.APIRetrofit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Adapter_ViewHolder.MyPropertyListAdapter
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

class MyProperty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_property)

        //Variables: (Textfield, EditText)
        val backIcon = findViewById<ImageView>(R.id.back_icon);
        //Funcion Btn.
        backIcon.setOnClickListener(){
            finish()
        }

        //Lista de porperties:
        val MyPropertyList = mutableListOf<GetProperty>();

        //Email del usuario.
        val loginEmail = UserGlobal.UserLoginInfo.userEmail

        //Api para leer la lista de properties.
        apiPropertyList(MyPropertyList,loginEmail)

    }

    fun apiPropertyList(MyPropertyList: MutableList<GetProperty>,loginEmail: String){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadMyProperty("readMyProperties", User(0,"","","",loginEmail,"","","",""))
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val properties = respuesta.body()
                    if(properties != null){
                        MyPropertyList.addAll(properties)
                        //Configuracion RecyclerView.
                        val recyclerView = findViewById<RecyclerView>(R.id.MylistProperty);
                        recyclerView.layoutManager = LinearLayoutManager(applicationContext);
                        recyclerView.adapter = MyPropertyListAdapter(MyPropertyList);
                    }
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, "Error read properties.", duration)
                    toast.show()
                }
            }
        }
    }

}