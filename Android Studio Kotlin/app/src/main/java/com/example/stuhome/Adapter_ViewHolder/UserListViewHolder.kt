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
import com.example.stuhome.Activities.UserInfo
import com.example.stuhome.R
import model.User

class UserListViewHolder(view: View): RecyclerView.ViewHolder(view)  {
    val chat_user_name = view.findViewById<TextView>(R.id.user_chat_name)
    val chat_user_study = view.findViewById<TextView>(R.id.user_chat_study)
    val chat_user_img = view.findViewById<ImageView>(R.id.chat_user_img)

    fun render(user: User, context: Context){

        //Cargar Imagen desde Mysql de usuario por defecto. //Decodificar el imagen bytesArray a bitmap.
        val imageBytes = Base64.decode(user?.image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        chat_user_img.setImageBitmap(bitmap)

        chat_user_name.text = user.name
        chat_user_study.text = user.studies

        chat_user_img.setOnClickListener {
            val intent = Intent(context, UserInfo::class.java)
            intent.putExtra("USER_EMAIL", user.email)
            context.startActivity(intent)
        }
    }
}