package com.example.stuhome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


//class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
//        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout,viewGroup,false)
//        return ViewHolder(v);
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
//        viewHolder.itemName.text = "Name"
//        viewHolder.itemAddressCity.text = "Address city"
//        viewHolder.itemDescription.text = "Description"
//        viewHolder.itemPrice.text = "15/month"
//    }
//
//
//
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var itemImage: ImageView
//        var itemName: TextView
//        var itemAddressCity: TextView
//        var itemDescription: TextView
//        var itemPrice: TextView
//        init {
//            itemImage = itemView.findViewById(R.id.item_image)
//            itemName = itemView.findViewById(R.id.item_name)
//            itemAddressCity = itemView.findViewById(R.id.item_addressCity)
//            itemDescription = itemView.findViewById(R.id.item_description)
//            itemPrice = itemView.findViewById(R.id.item_price)
//        }
//    }
//
//}