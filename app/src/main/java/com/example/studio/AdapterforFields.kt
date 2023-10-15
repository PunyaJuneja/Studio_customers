package com.example.studio

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studio.models.Genre
import kotlinx.coroutines.currentCoroutineContext

class AdapterforFields(var items:ArrayList<Genre>,private val listener:ItemClickedField):RecyclerView.Adapter<ViewHolderField>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderField {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.itemforfields, parent, false)

        val viewHolderField = ViewHolderField(view)

        view.setOnClickListener {
            listener.onartistclicked(items[viewHolderField.adapterPosition],items[viewHolderField.adapterPosition].uid,items[viewHolderField.adapterPosition].uername)
        }
        return viewHolderField
    }

    override fun onBindViewHolder(holder: ViewHolderField, position: Int) {
        val current = items[position]
        holder.name.text = current.uername
//        holder.button.setOnClickListener {
//            val intentmessage = Intent(holder.itemView.context, ArtistProfileUsers::class.java)
//            intentmessage.putExtra("Username", "${current.uid}")
//            holder.itemView.context.startActivity(intentmessage)
//         val intent = Intent(holder.itemView.context,ArtistProfileUsers::class.java)
//            holder.itemView.context.startActivity(intent)

        // Glide.with(holder.itemView.context).load(current.url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }


}
interface ItemClickedField {
    fun onartistclicked(item:Genre,id:String,name:String)

}

class ViewHolderField(itemview: View):RecyclerView.ViewHolder(itemview) {
val image = itemview.findViewById<ImageView>(R.id.artistpic)
    val name=itemview.findViewById<TextView>(R.id.artistname)
    val button = itemview.findViewById<Button>(R.id.book)
    }



