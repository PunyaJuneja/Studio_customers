package com.example.studio

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.studio.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignInActivityUsers : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var users:HashSet<String>
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_users)
        users= hashSetOf()
        val button=findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button)
        val email=findViewById<EditText>(R.id.email)
        val password=findViewById<EditText>(R.id.password)

        val already=findViewById<TextView>(R.id.already)

        firestore = FirebaseFirestore.getInstance()
        firestore.collection("UsersandArtists").
        get().
        addOnSuccessListener {
            for(doc in it)
            {
                val x= doc.toObject(User::class.java)
                if(x.genre==null||x.genre=="user")
                {
                    users.add(x.email)
                }
            }
        }

        firebaseAuth=FirebaseAuth.getInstance()
        already.setOnClickListener{
            val intent= Intent(this,SignUpActivityUsers::class.java)
            startActivity(intent)
        }
        button.setOnClickListener{
            val emailn=email.text.toString()
            val passwordn=password.text.toString()


            if(emailn.isNotEmpty() && passwordn.isNotEmpty() )
            {

                if(users.contains(emailn)) {
                    firebaseAuth.signInWithEmailAndPassword(emailn, passwordn)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                }
                else
                {
                    Toast.makeText(this,"Not an User!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Please fill all the fields!", Toast.LENGTH_SHORT).show()
            }

        }


    }

}