package com.example.studio
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import com.example.studio.Daos.UserDao
import com.example.studio.models.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    var result:Boolean=true
    var getmessage:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        val bottomnav=findViewById(R.id.bottomnav) as BottomNavigationView

        bottomnav.selectedItemId=R.id.home
        bottomnav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home -> {
                   val i= Intent(this,HomeActivity::class.java)
                    startActivity(i)
                   return@setOnNavigationItemReselectedListener
                }
                R.id.booking -> {
                    val i= Intent(this,BookingActivity::class.java)
                    startActivity(i)
                    return@setOnNavigationItemReselectedListener
                }
            }

    }}
        override fun onStart() {
            super.onStart()
            val user = auth.currentUser
            updateUI(user)
        }
        private fun updateUI(firebaseUser: FirebaseUser?) {
            if(firebaseUser != null) {


                val mainActivityIntent = Intent(this, HomeActivity::class.java)
                startActivity(mainActivityIntent)
            }
                else
                {
                    val mainActivityIntent = Intent(this, SignUpActivityUsers::class.java)
                    startActivity(mainActivityIntent)
                }
//            val google=findViewById<com.google.android.gms.common.SignInButton>(R.id.buttonforgoogle)
//            google.visibility = View.VISIBLE
//            val progress=findViewById<ProgressBar>(R.id.progress)
//            progress.visibility=View.GONE


        }

}