package com.example.studio.Daos

import android.content.ContentValues.TAG
import android.util.Log
import com.example.studio.datatype
import com.example.studio.models.Genre
import com.example.studio.models.User
import com.example.studio.models.artistinuser
import com.example.studio.models.userinartist
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserDao {
    var str:String=""
    val db =FirebaseFirestore.getInstance()
    private lateinit var items:ArrayList<User>
    val userscollection = db.collection("UsersandArtists")
    fun adduser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO){
                userscollection.document(user.uid).set(it)
            }
        }
    }

    fun addartistinuser(userid:String,artistinuser:artistinuser)
    {
        val userbookings=db.collection("UserBookings").document(userid).collection("Bookings")
            artistinuser.let {
                GlobalScope.launch(Dispatchers.IO) {
                  userbookings.document(artistinuser.uid).set(it)
                }
            }
    }

    fun adduserinartist(userid:String,artistid:String,userinartist: userinartist,field:String)
    {

        val artistcollection = db.collection("Fields").document(field).collection("Artists").document(artistid).collection("users")
        userinartist.let {
            GlobalScope.launch(Dispatchers.IO){
                artistcollection.document(userinartist.uid).set(it)
            }
        }

    }

    fun addusertogenre(field:String,genre: Genre?)
    {
        val fieldcollection = db.collection("Fields").document(field).collection("Artists")
        genre?.let {
            GlobalScope.launch(Dispatchers.IO) {
                fieldcollection.document(genre.uid).set(it)

            }
        }

    }

    suspend fun artistoruser(user:FirebaseUser):Boolean {

      //  var result: Boolean = true
        val doc = db.collection("UsersandArtists").document(user.uid).get().await()
        val obj= doc?.toObject(User::class.java)
        if (obj != null) {
            if (obj.genre == null || obj.genre == "user")
                return true
        }
        return false
    }

    fun declinerequest(artistid:String,user:userinartist,field:String)
    {
    val artistcollection =db.collection("Fields").document(field).collection("Artists").document(artistid).collection("users")
    user?.let{
        GlobalScope.launch(Dispatchers.IO){
            artistcollection.document(user.uid).delete()
        }
    }
    }

//        db.collection("UsersandArtists").get().addOnSuccessListener {
//
//            for(doc in it)
//            {
//                if(doc.id==user.uid)
//                {
//                    val obj = doc.toObject(User::class.java)
//
//                    if(obj.genre==null||obj.genre=="user")
//                    {
//                     swap("user")
//                    }
//                    break
//                }
//            }
//
//
//        }
//
//        Log.e(TAG,"value= " +str)


}