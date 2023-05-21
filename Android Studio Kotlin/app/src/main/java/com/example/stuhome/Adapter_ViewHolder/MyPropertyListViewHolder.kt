package com.example.stuhome.Adapter_ViewHolder

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Activities.DeleteProperty
import com.example.stuhome.Activities.UpdateProperty
import com.example.stuhome.R
import model.GetProperty

class MyPropertyListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val myProperty_image = view.findViewById<ImageView>(R.id.myProperty_image)
    val myProperty_name = view.findViewById<TextView>(R.id.myProperty_name)
    val myProperty_address = view.findViewById<TextView>(R.id.myProperty_address)
    val myProperty_city = view.findViewById<TextView>(R.id.myProperty_City)
    val myProperty_description = view.findViewById<TextView>(R.id.myProperty_description)
    val editProperty_btn = view.findViewById<AppCompatButton>(R.id.editProperty_btn)
    val deleteProperty_btn = view.findViewById<AppCompatButton>(R.id.deleteProperty_btn)

    fun render(property: GetProperty, context: Context) {
        //Cargar Imagen desde Mysql de usuario por defecto. //Decodificar el imagen bytesArray a bitmap.
        val imageBytes = Base64.decode(property?.image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        myProperty_image.setImageBitmap(bitmap)

        // Establecer otros campos
        myProperty_description.text = property.property_description
        myProperty_name.text = property.property_name
        myProperty_address.text = property.property_address
        myProperty_city.text = property.property_city

        editProperty_btn.setOnClickListener {
            val intent = Intent(context, UpdateProperty::class.java)
            intent.putExtra("PROPERTY_OBJECT", property)
            context.startActivity(intent)
        }

        deleteProperty_btn.setOnClickListener {
            val intent = Intent(context, DeleteProperty::class.java)
            intent.putExtra("PROPERTY_OBJECT", property)
            context.startActivity(intent)
        }
    }
}