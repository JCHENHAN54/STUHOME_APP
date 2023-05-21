package com.example.stuhome.Adapter_ViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Adapter_ViewHolder.PropertyListViewHolder
import com.example.stuhome.R
import model.User

class UserListAdapter(private val userList:List<User>) : RecyclerView.Adapter<UserListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserListViewHolder(layoutInflater.inflate(R.layout.userlist_item,parent,false))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = userList[position]
        val context = holder.itemView.context
        holder.render(item,context);
    }

}