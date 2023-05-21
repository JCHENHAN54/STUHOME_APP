package com.example.stuhome.Fragments

import Retrofit.APIRetrofit
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Adapter_ViewHolder.MyPropertyListAdapter
import com.example.stuhome.Adapter_ViewHolder.UserListAdapter
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

class ChatFragment : Fragment() {

    private val chatUserList = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //Api para leer la lista de usuarios.
        apiChatUserList(chatUserList)

        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    fun apiChatUserList(chatUserList: MutableList<User>){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/user/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadAllUser("readAllUsers")
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val users = respuesta.body()
                    if(users != null){
                        chatUserList.addAll(users)
                        //Configuracion RecyclerView.
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.UsersChatList);
                        recyclerView?.layoutManager = LinearLayoutManager(context);
                        recyclerView?.adapter = UserListAdapter(chatUserList);
                    }else{
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, "There are no users.", duration)
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