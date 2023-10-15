package com.example.studio

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), ItemClicked {
    private lateinit var vadapter:Adapter
    private lateinit var items:ArrayList<datatype>
    private lateinit var database:DatabaseReference
    private lateinit var adapter:Adapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recycler:androidx.recyclerview.widget.RecyclerView
   // private lateinit var  list:ArrayList<datatype>
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(ContentValues.TAG,"create called")
        val progressbar =findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.progressbar)
        progressbar.visibility=View.VISIBLE
        items = arrayListOf()
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Fields")
            .get()
            .addOnSuccessListener {
               items.clear()

                for(doc in it)
                {
                    val datatype=doc.toObject(datatype::class.java)
                    items.add(datatype)
                }

                 recycler= findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler)
                recycler.layoutManager=GridLayoutManager(this,2,)

                adapter=Adapter(items,this)
                recycler.adapter=adapter
            }
            .addOnFailureListener{
                it.printStackTrace()
                Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener{
                progressbar.visibility=View.GONE
            }
        //val adapter=Adapter(items)

//        val recycler= findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler)
//        recycler.layoutManager=GridLayoutManager(this,2,)
//        val adapter=Adapter(items,this)
//        recycler.adapter=adapter
//       recycler.adapter=Adapter(items,this)
//       recycler

        val bottomnav=findViewById(R.id.hbottomnav) as BottomNavigationView
        bottomnav.menu.findItem(R.id.home).setChecked(true)
       // bottomnav.selectedItemId=R.id.home
        bottomnav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.home -> {
//                    val i= Intent(this,HomeActivity::class.java)
//                    startActivity(i)

                }
                R.id.booking -> {
                    val i= Intent(this,BookingActivity::class.java)
                    startActivity(i)
                //    finish()
                 //   return@setOnNavigationItemSelectedListener
                }

            }
            true
    }
        val  search = findViewById<SearchView>(R.id.search)

        //search.clearFocus()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterlist(newText)

                return true
            }
        })
     //   search.clearFocus()

}
    private fun filterlist(newText: String?) {


        if (newText != null) {
            val filteredList = ArrayList<datatype>()
            for (i in items) {
                if (i.Name?.lowercase(Locale.ROOT)?.contains(newText) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateitems(filteredList)
            }
        }

//       temp.clear()
//
//        items.forEach {
//
//            if (newText != null) {
//                if(it.Name?.toLowerCase(Locale.getDefault())?.contains(newText) == true)
//                {
//                    temp.add(it)
//                }
//            }
//
//        }
//
//       if(temp.isEmpty())
//       {
//           temp.clear()
//           temp.addAll(fixeditems)
//       }
//
//        adapter.updateitems(temp)

            }



    override fun onStart() {
        super.onStart()
        Log.d(ContentValues.TAG,"Start called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(ContentValues.TAG,"Stop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(ContentValues.TAG,"Destroy called")
    }

    override fun onBackPressed() {
        finish()
        System.exit(0)
    }



    override fun onItemclicked(item: datatype) {
        val intentmessage = Intent(this, FieldResultUsers::class.java)
        intentmessage.putExtra("Username", "${item.Name}")
        startActivity(intentmessage)

    }
}