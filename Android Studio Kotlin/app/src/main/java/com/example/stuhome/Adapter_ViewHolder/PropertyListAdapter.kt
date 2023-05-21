

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stuhome.Adapter_ViewHolder.PropertyListViewHolder
import com.example.stuhome.R
import model.GetProperty


class PropertyListAdapter(private val propertyList:List<GetProperty>) : RecyclerView.Adapter<PropertyListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PropertyListViewHolder(layoutInflater.inflate(R.layout.propertyhome_item,parent,false))
    }

    override fun getItemCount(): Int = propertyList.size

    override fun onBindViewHolder(holder: PropertyListViewHolder, position: Int) {
        val item = propertyList[position]
        val context = holder.itemView.context
        holder.render(item,context);
    }

}