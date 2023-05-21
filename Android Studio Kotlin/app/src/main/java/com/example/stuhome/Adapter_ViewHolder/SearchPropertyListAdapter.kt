package com.example.stuhome.Adapter_ViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.R
import model.GetProperty

class SearchPropertyListAdapter(private var propertyList:List<GetProperty>): RecyclerView.Adapter<SearchPropertyListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPropertyListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchPropertyListViewHolder(layoutInflater.inflate(R.layout.searchproperty_item,parent,false))
    }

    fun setFilteredList(filteredList: MutableList<GetProperty>) {
        this.propertyList = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = propertyList.size

    override fun onBindViewHolder(holder: SearchPropertyListViewHolder, position: Int) {
        val item = propertyList[position]
        val context = holder.itemView.context
        holder.render(item,context);
    }
}