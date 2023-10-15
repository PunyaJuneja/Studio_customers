package com.example.studio

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.api.Context

class Adapter( var items:ArrayList<datatype>,private val listener:ItemClicked):RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val viewholder=ViewHolder(view)
        view.setOnClickListener{
            listener.onItemclicked(items[viewholder.adapterPosition])
        }
      return viewholder
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val current = items[position]
        holder.text.text = current.Name
        Glide.with(holder.itemView.context).load(current.url).into(holder.image)
    }

    override fun getItemCount(): Int {
      return items.size
    }
    fun updateitems( items :ArrayList<datatype>)
    {
        this.items=items
        notifyDataSetChanged()
    }
}
class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview)
{
    val text = itemview.findViewById<TextView>(R.id.title)
    val image= itemview.findViewById<ImageView>(R.id.imagefield)


}
interface ItemClicked{
    fun onItemclicked(item:datatype)
}