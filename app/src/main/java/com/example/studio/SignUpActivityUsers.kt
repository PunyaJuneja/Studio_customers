package com.example.studio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.studio.Daos.UserDao
import com.example.studio.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignUpActivityUsers : AppCompatActivity() {
    private val rc_sign_in:Int=123
    private val TAG = "SignInActivity Tag"
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_users)
        auth = Firebase.auth
        firebaseAuth=FirebaseAuth.getInstance()
        val button=findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.button)
        val email=findViewById<EditText>(R.id.email)
        val password=findViewById<EditText>(R.id.password)
        val confirm=findViewById<EditText>(R.id.confirm)
        val already=findViewById<TextView>(R.id.already)
        val google=findViewById<com.google.android.gms.common.SignInButton>(R.id.buttonforgoogle)



        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this , gso)


        google.setOnClickListener {
            signInGoogle()
        }


        already.setOnClickListener{
            val intent=Intent(this,SignInActivityUsers::class.java)
            startActivity(intent)
        }
        button.setOnClickListener{
            val emailn=email.text.toString()
            val passwordn=password.text.toString()
            val confirmn=confirm.text.toString()

            if(emailn.isNotEmpty() && passwordn.isNotEmpty() && confirmn.isNotEmpty())
            {
                if(passwordn.equals(confirmn))
                {

                firebaseAuth.createUserWithEmailAndPassword(emailn,passwordn).addOnCompleteListener{
                    if(it.isSuccessful)
                    {

                        val firebaseUser=firebaseAuth.currentUser
                        val user= firebaseUser?.email?.let { User(firebaseUser.uid, it,"user") }
                        val userDao=UserDao()
                        userDao.adduser(user)
                        val intent= Intent(this,SignInActivityUsers::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                }
                else{
                    Toast.makeText(this,"Password is not matching",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Please fill all the fields!",Toast.LENGTH_SHORT).show()
            }

        }

    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,rc_sign_in)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == rc_sign_in) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)!!

          //  Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }}

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val google=findViewById<com.google.android.gms.common.SignInButton>(R.id.buttonforgoogle)
        val progress=findViewById<ProgressBar>(R.id.progress)
        progress.visibility=View.VISIBLE
        google.visibility = View.GONE

        GlobalScope.launch(Dispatchers.IO) {
            val auth = auth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main) {
                updateUI(firebaseUser)
            }
        }

    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {

            val user= firebaseUser.email?.let { User(firebaseUser.uid, it,"user") }
            val userDao=UserDao()
            userDao.adduser(user)
            val mainActivityIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainActivityIntent)
           // finish()
        } else {
//            val google=findViewById<com.google.android.gms.common.SignInButton>(R.id.buttonforgoogle)
//            google.visibility = View.VISIBLE
//            val progress=findViewById<ProgressBar>(R.id.progress)
//            progress.visibility=View.GONE

        }
    }

    override fun onBackPressed() {
        finish()
        System.exit(0)
    }
}