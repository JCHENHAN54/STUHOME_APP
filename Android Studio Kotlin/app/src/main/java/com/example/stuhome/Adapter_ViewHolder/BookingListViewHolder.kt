package com.example.stuhome.Adapter_ViewHolder

import Global.UserGlobal
import Retrofit.APIRetrofit
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Activities.DeleteProperty
import com.example.stuhome.Activities.UpdateProperty
import com.example.stuhome.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Booking
import model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookingListViewHolder(view: View): RecyclerView.ViewHolder(view)  {

    val booking_name = view.findViewById<TextView>(R.id.booking_name)
    val booking_description = view.findViewById<TextView>(R.id.booking_description)
    val booking_price = view.findViewById<TextView>(R.id.booking_price)

    fun render(booking: Booking, context: Context) {
        // Establecer otros campos
        booking_name.text = booking.property_booking_name
        booking_description.text = booking.property_booking_description
        booking_price.text = booking.property__price.toString() + " $/month"
    }
}