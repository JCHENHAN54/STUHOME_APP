package com.example.stuhome.Fragments

import Global.UserGlobal
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
import com.example.stuhome.Adapter_ViewHolder.BookingListAdapter
import com.example.stuhome.Adapter_ViewHolder.SearchPropertyListAdapter
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Booking
import model.GetProperty
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)
        //Aqui es Donde se pone las creaciones de variables y funciones. funciona igualmente
        // que en los activitys.

        val bookingList = mutableListOf<Booking>()
        //Email del usuario.
        val loginEmail = UserGlobal.UserLoginInfo.userEmail

        apiBookingList(bookingList,loginEmail)


        return view
    }

    fun apiBookingList(bookingList: MutableList<Booking>,loginEmail: String){
        //Codigo Retrofit:
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val conexion = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/booking/")
                .addConverterFactory(
                    GsonConverterFactory.create()).client(client).build()
            var respuesta = conexion.create(APIRetrofit::class.java)
                .ApiReadMyBookings("readMyBookings",User(0,"","","",loginEmail,"","","",""))
            withContext(Dispatchers.Main) {
                //SI el usuario ha creado su cuenta correctamente, pues ira a la pagina de home de applicacion.
                if (respuesta.isSuccessful) {
                    val bookings = respuesta.body()
                    if(bookings != null){
                        bookingList.addAll(bookings)
                        //Configuracion RecyclerView.
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.listBooking);
                        recyclerView?.layoutManager = LinearLayoutManager(context);
                        recyclerView?.adapter = BookingListAdapter(bookingList);
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

