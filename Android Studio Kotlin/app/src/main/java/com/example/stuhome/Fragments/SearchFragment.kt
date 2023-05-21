package com.example.stuhome.Fragments

import Retrofit.APIRetrofit
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Adapter_ViewHolder.SearchPropertyListAdapter
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.GetProperty
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var propertyListAdapter: SearchPropertyListAdapter
    private val propertyList = mutableListOf<GetProperty>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.

        //Configuracion de SearchView:
        searchView = view.findViewById(R.id.searchView)
        searchView.clearFocus()

        //Api para leer la lista de properties.
        apiSearchPropertyList(propertyList)

        //Configuracion RecyclerView.
        val recyclerView = view.findViewById<RecyclerView>(R.id.listSearchProperty)
        recyclerView.layoutManager = LinearLayoutManager(context)
        propertyListAdapter = SearchPropertyListAdapter(propertyList)
        recyclerView.adapter = propertyListAdapter

        // Configurar SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredList: MutableList<GetProperty> = ArrayList()
                    for (item in propertyList) {
                        if(item.property_name.toLowerCase().contains(newText.toLowerCase()) ||
                            item.property_city.toLowerCase().contains(newText.toLowerCase()) ||
                            item.property_address.toLowerCase().contains(newText.toLowerCase())){
                            filteredList.add(item)
                        }
                    }
                    if (filteredList.isEmpty()) {
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, "There is no property found.", duration)
                        toast.show()
                    } else {
                        propertyListAdapter.setFilteredList(filteredList)
                        propertyListAdapter.notifyDataSetChanged()
                    }
                }
                return true
            }
        })

        // Inflate the layout for this fragment
        return view
    }

    fun onBackPressed() {
        // No hacemos nada
    }

    fun apiSearchPropertyList(propertyList: MutableList<GetProperty>){
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
                        propertyListAdapter.notifyDataSetChanged()
                    }else{
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, "There are no properties.", duration)
                        toast.show()
                    }
                } else {
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(context, "Error read properties", duration)
                    toast.show()
                }
            }
        }
    }
}
