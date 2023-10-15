package com.example.studio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.studio.Daos.UserDao
import com.example.studio.models.Genre
import com.example.studio.models.User
import com.example.studio.models.artistinuser
import com.example.studio.models.userinartist
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ArtistProfileUsers : AppCompatActivity() {
    var artistname:String=""
    var artistid:String=""
    var field:String=""
    lateinit var auth:FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var currentuser:FirebaseUser
    var currentuserid:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_profile_users)
        artistid = intent.getStringExtra("Username").toString()
        field = intent.getStringExtra("field").toString()
        artistname=intent.getStringExtra("artistname").toString()
        auth=FirebaseAuth.getInstance()
         currentuser = auth.currentUser!!
        currentuserid=currentuser.uid

        val button=findViewById<Button>(R.id.BookArtist)
        button.setOnClickListener {
            val user = userinartist(currentuserid, currentuser.email.toString())
            val artist=artistinuser(artistid,field,artistname)
            val userDao = UserDao()
            userDao.addartistinuser(currentuserid,artist)
            userDao.adduserinartist(currentuserid, artistid, user, field)
            Toast.makeText(this,"Booking done",Toast.LENGTH_SHORT).show()
        }
        firestore = FirebaseFirestore.getInstance()
        val name= findViewById<TextView>(R.id.username)
        val bookingcount=findViewById<TextView>(R.id.bookingcount)
        firestore.collection("Fields").document(field).collection("Artists").get().addOnSuccessListener {
            for(doc in it)
            {
                val x = doc.toObject(Genre::class.java)
                if(x.uid==artistid)
                    name.text=x.uername

            }
        }





    }
}