package com.example.studio

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studio.fragments.AccountFragment
import com.example.studio.fragments.BookingsFragment
import com.example.studio.models.Genre
import com.example.studio.models.artistinuser
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class BookingActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var items: ArrayList<artistinuser>
    lateinit var firestore: FirebaseFirestore
    lateinit var currentuser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        Log.d(TAG,"bookings create called")
        val bottomnav=findViewById<BottomNavigationView>(R.id.bbottomnav)
        bottomnav.menu.findItem(R.id.booking).setChecked(true)
       // bottomnav.selectedItemId=R.id.booking
//        items= arrayListOf()
        auth= Firebase.auth
        currentuser=auth.currentUser!!
        firestore=FirebaseFirestore.getInstance()
//        firestore.collection("UserBookings").document(currentuser.uid).collection("Bookings").get().addOnSuccessListener {
//            for(doc in it)
//            {
//                val artist=doc.toObject(artistinuser::class.java)
//                items.add(artist)
//            }
//            val recyler=findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recylerbooking)
//            recyler.layoutManager=LinearLayoutManager(this)
//            val adapter=AdapterforBooking(items,this)
//            recyler.adapter=adapter
//        }
        bottomnav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val i= Intent(this,HomeActivity::class.java)
                    startActivity(i)
                   // finish()

                }
                R.id.booking -> {
                    val i= Intent(this,BookingActivity::class.java)
                    startActivity(i)

                }
            }
            true
    }


        val tablayout=findViewById<com.google.android.material.tabs.TabLayout>(R.id.TabLayout)
        val viewpager=findViewById<androidx.viewpager.widget.ViewPager>(R.id.ViewPager)


        val fragmentAdapter = Adapterfortabs(supportFragmentManager)
        fragmentAdapter.addFragment(BookingsFragment(),"Bookings")
        fragmentAdapter.addFragment(AccountFragment(),"Account")


        viewpager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewpager)



        val signout=findViewById<Button>(R.id.signout)
        signout.setOnClickListener{

        auth.signOut()

                val intent=Intent(this,SignUpActivityUsers::class.java)
                startActivity(intent)

        }
}

    override fun onStart() {
        super.onStart()
        Log.d(ContentValues.TAG,"bookin Start called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(ContentValues.TAG,"booking Stop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(ContentValues.TAG,"bookin Destroy called")
    }

//
//    override fun onBookingclicked(item:artistinuser) {
//
//        Toast.makeText(this,"Booking clicked ${item.name}",Toast.LENGTH_SHORT).show()
//    }
}