package com.example.studio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studio.models.artistinuser

class AdapterforBooking(var items:ArrayList<artistinuser>,private val listener:ItemClickedBooking):RecyclerView.Adapter<ViewHolderBooking>()
 {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBooking {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.itemforfields, parent, false)
         val viewholder = ViewHolderBooking(view)
         view.setOnClickListener {
             listener.onBookingclicked(items[viewholder.adapterPosition])
         }
         return viewholder

     }


     override fun getItemCount(): Int {
        return items.size
     }


     override fun onBindViewHolder(holder: ViewHolderBooking, position: Int) {
         val current=items[position]
         holder.name.text=current.name
         holder.genre.text=current.genre

     }

 }

interface ItemClickedBooking {
    fun onBookingclicked(item:artistinuser)

}

class ViewHolderBooking(itemview: View):RecyclerView.ViewHolder(itemview) {
    val image = itemview.findViewById<ImageView>(R.id.artistpic)
    val name=itemview.findViewById<TextView>(R.id.artistname)
   // val button = itemview.findViewById<Button>(R.id.book)
    val genre = itemview.findViewById<TextView>(R.id.genre)

}
