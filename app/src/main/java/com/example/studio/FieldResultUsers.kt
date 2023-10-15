package com.example.studio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studio.models.Genre
import com.example.studio.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore

class FieldResultUsers : AppCompatActivity(), ItemClickedField {
    var getmessage: String = ""

    private lateinit var items: ArrayList<Genre>
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_field_result_users)
        val progressbar =findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.progressbarfield)
        progressbar.visibility= View.VISIBLE
        items= arrayListOf()
        getmessage = intent.getStringExtra("Username").toString()
        Toast.makeText(this,"item clicked ${getmessage}",Toast.LENGTH_LONG).show()
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Fields").document(getmessage).collection("Artists").get().addOnSuccessListener {
            items.clear()
            for(doc in it)
            {
                val x= doc.toObject(Genre::class.java)
                items.add(x)
            }
            val recycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerfield)
            recycler.layoutManager=LinearLayoutManager(this)
            val adapter= AdapterforFields(items,this)
            recycler.adapter=adapter
        }.addOnCompleteListener{
            progressbar.visibility=View.GONE
        }
//        val intentmessage = Intent(this, ArtistProfileUsers::class.java)
//        intentmessage.putExtra("field", "${getmessage}")
//        startActivity(intentmessage)



    }

    override fun onartistclicked(item: Genre,id:String,name:String) {
      //  Toast.makeText(this,"item clicked ${item.uername}",Toast.LENGTH_LONG).show()
        Toast.makeText(this,"${getmessage}",Toast.LENGTH_LONG).show()
        val intentmessage = Intent(this, ArtistProfileUsers::class.java)
            intentmessage.putExtra("Username", id)
            intentmessage.putExtra("field","${getmessage}")
        intentmessage.putExtra("artistname",name)

         startActivity(intentmessage)
    }
}