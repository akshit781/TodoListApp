package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.todo_item.view.*
import java.util.ArrayList

open class MainActivity : AppCompatActivity() {

    lateinit var userId : String
    lateinit var userData: Map<String, Any>

    lateinit var listView: ListView
    lateinit var todoAdapter: TodoAdapter

    var toDoList = ArrayList<ToDoItem>()

    var pos = 0;

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
        listView = findViewById(R.id.id_listView)
        todoAdapter = TodoAdapter(this, R.layout.todo_item, toDoList)

        listView.adapter= todoAdapter
        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            pos = position
        }
    }

    class TodoAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val todoItems: List<ToDoItem>):
        ArrayAdapter<ToDoItem>(context, layoutResource, todoItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createViewFromResource(position, convertView, parent)
        }

        private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View{
            val view: View = LayoutInflater.from(context).inflate(layoutResource, parent, false)

            view.id_tTitle.setText(todoItems[position].title)
            view.id_tTime.setText(todoItems[position].time.toString())
            view.id_checkBox.isChecked = todoItems[position].isCompleted

            return view
        }
    }
}