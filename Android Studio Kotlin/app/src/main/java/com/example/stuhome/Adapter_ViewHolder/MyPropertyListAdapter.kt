package com.example.stuhome.Adapter_ViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.R
import model.GetProperty

class MyPropertyListAdapter(private val MyPropertyList: MutableList<GetProperty>) : RecyclerView.Adapter<MyPropertyListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPropertyListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyPropertyListViewHolder(layoutInflater.inflate(R.layout.myproperty_item,parent,false))
    }

    override fun getItemCount(): Int = MyPropertyList.size

    override fun onBindViewHolder(holder: MyPropertyListViewHolder, position: Int) {
        val item = MyPropertyList[position]
        val context = holder.itemView.context
        holder.render(item,context);
    }

}