package com.example.studio.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studio.AdapterforBooking
import com.example.studio.ItemClickedBooking
import com.example.studio.R
import com.example.studio.models.artistinuser
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class BookingsFragment : Fragment(), ItemClickedBooking {

    lateinit var recycler:androidx.recyclerview.widget.RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var items: ArrayList<artistinuser>
    lateinit var firestore: FirebaseFirestore
    lateinit var currentuser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       val progressbar =view.findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.progressbarf)
        progressbar.visibility=View.VISIBLE
        super.onViewCreated(view, savedInstanceState)
        getdata()
        recycler = view.findViewById(R.id.recyclerbookingf)
        recycler.layoutManager=LinearLayoutManager(requireContext())
    }
   fun getdata()
   {
       items= arrayListOf()
       auth= Firebase.auth
       currentuser=auth.currentUser!!
       firestore=FirebaseFirestore.getInstance()
       firestore.collection("UserBookings").document(currentuser.uid).collection("Bookings").get().addOnSuccessListener {
           for(doc in it)
           {
               val artist=doc.toObject(artistinuser::class.java)
               items.add(artist)
           }


       }.addOnCompleteListener{
           val progressbar = requireView().findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.progressbarf)

           progressbar.visibility=View.GONE
           val adapter= AdapterforBooking(items,this)
           recycler.adapter=adapter
       }
   }

    override fun onBookingclicked(item: artistinuser) {
        Toast.makeText(requireContext(),"Booking clicked ${item.name}", Toast.LENGTH_SHORT).show()
    }

}