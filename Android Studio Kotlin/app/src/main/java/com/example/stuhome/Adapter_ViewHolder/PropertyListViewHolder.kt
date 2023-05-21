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

    class PropertyListViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val property_image = view.findViewById<ImageView>(R.id.item_image)
        val property_name = view.findViewById<TextView>(R.id.item_name)
        val property_address = view.findViewById<TextView>(R.id.item_address)
        val property_city = view.findViewById<TextView>(R.id.item_city)
        val property_price = view.findViewById<TextView>(R.id.item_price)
        val property_description = view.findViewById<TextView>(R.id.item_description)

        fun render(property: GetProperty,context: Context){
            //Cargar Imagen desde Mysql de usuario por defecto. //Decodificar el imagen bytesArray a bitmap.
            val imageBytes = Base64.decode(property?.image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            property_image.setImageBitmap(bitmap)

            property_description.text = property.property_description
            property_name.text = property.property_name
            property_address.text = property.property_address
            property_city.text = property.property_city
            property_price.text = property.property_price.toString() + " $/month"

            property_image.setOnClickListener {
                val intent = Intent(context, PropertyDetails::class.java)
                intent.putExtra("PROPERTY_OBJECT", property)
                context.startActivity(intent)
            }
        }
}