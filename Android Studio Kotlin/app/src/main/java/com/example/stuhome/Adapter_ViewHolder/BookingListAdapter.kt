package com.example.stuhome.Adapter_ViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.R
import model.Booking
import model.GetProperty

class BookingListAdapter(private val MyBookingList: MutableList<Booking>) : RecyclerView.Adapter<BookingListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookingListViewHolder(layoutInflater.inflate(R.layout.bookingproperty_item,parent,false))
    }

    override fun getItemCount(): Int = MyBookingList.size

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) {
        val item = MyBookingList[position]
        val context = holder.itemView.context
        holder.render(item,context);
    }

}