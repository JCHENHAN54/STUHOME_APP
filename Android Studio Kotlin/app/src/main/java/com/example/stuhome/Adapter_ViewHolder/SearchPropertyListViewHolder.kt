package com.example.stuhome.Adapter_ViewHolder

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Activities.PropertyDetails
import com.example.stuhome.R
import model.GetProperty

class SearchPropertyListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val search_property_image = view.findViewById<ImageView>(R.id.search_property_img)
    val search_property_name = view.findViewById<TextView>(R.id.search_property_name)
    val search_property_address = view.findViewById<TextView>(R.id.search_property_address)
    val search_property_price = view.findViewById<TextView>(R.id.search_property_price)
    val search_property_city = view.findViewById<TextView>(R.id.search_property_city)

    fun render(property: GetProperty, context: Context){
        //Cargar Imagen desde Mysql de usuario por defecto. //Decodificar el imagen bytesArray a bitmap.

        val imageBytes = Base64.decode(property?.image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        search_property_image.setImageBitmap(bitmap)

        search_property_name.text = property.property_name
        search_property_address.text = property.property_address
        search_property_city.text = property.property_city
        search_property_price.text = property.property_price.toString() + " $/month"

        search_property_image.setOnClickListener {
            val intent = Intent(context, PropertyDetails::class.java)
            intent.putExtra("PROPERTY_OBJECT", property)
            context.startActivity(intent)
        }

    }

}