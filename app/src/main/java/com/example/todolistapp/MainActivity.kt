package com.example.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class MainActivity : AppCompatActivity() {

    var userId : String? = null;
    var userData: Map<String, Any>? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, GoogleSignInActivity::class.java))
//        setContentView(R.layout.activity_main)
//
//        //val signInActivity = Intent(this, MainActivity::class.java)
//        //startActivityForResult(signInActivity, )
//
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
