package com.example.studio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import java.lang.Thread.sleep
import java.util.logging.Handler
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sam)
        auth = Firebase.auth
        val user = auth.currentUser
        supportActionBar?.hide()
        android.os.Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity


                    val intent:Intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
        },2000)
            }
}