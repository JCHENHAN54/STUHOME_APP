package com.example.stuhome.Fragments

import PropertyListAdapter
import Retrofit.APIRetrofit
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Activities.AboutUs
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.GetProperty
import model.Property
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.
        //Lista de porperties:
        val propertyList = mutableListOf<GetProperty>();
        //Api para leer la lista de properties.
        apiPropertyList(propertyList)

        val aboutUsIcon: ImageView = view.findViewById<ImageView>(R.id.aboutUs_icon)
        aboutUsIcon.setOnClickListener{
            val intent = Intent(activity, AboutUs::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

    fun onBackPressed() {
        // No hacemos nada
    }

    fun apiPropertyList(propertyList: MutableList<GetProperty>){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/property/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadAllProperty("readProperties")
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val properties = respuesta.body()
                    if(properties != null){
                        propertyList.addAll(properties)
                        //Configuracion RecyclerView.
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.listProperty);
                        //recyclerView.layoutManager = LinearLayoutManager(context);
                        recyclerView?.layoutManager = GridLayoutManager(context,2);
                        recyclerView?.adapter = PropertyListAdapter(propertyList);
                    }else{
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, "There are no properties.", duration)
                        toast.show()
                    }
                }else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, "Error read properties", duration)
                    toast.show()
                }
            }
        }
    }

}