package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class MainActivity : AppCompatActivity() {

    var userId : String? = null;
    var userData: Map<String, Any>? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInActivity = Intent(this, GoogleSignInActivity::class.java)
        startActivity(signInActivity)
//
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            Log.d("TAG", "${user.email}")
//        } else {
//            startActivity(signInActivity)
//        }

//        val db = Firebase.firestore
//        db.collection("Users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("TAG", "${document.id} => ${document.data}")
//                    userId = document.id;
//                    userData = document.data;
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("TAG", "Error getting documents.", exception)
//            }



    }
}
