package com.example.todolistapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.todo_item.view.*
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

open class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView

    lateinit var bRefresh: Button
    lateinit var bAdd: Button

    lateinit var etTitle: EditText
    lateinit var etDescription: EditText
    lateinit var etLocation: EditText
    lateinit var etTime: EditText

    lateinit var userId : String
    lateinit var userData: Map<String, Any>

    lateinit var todoAdapter: TodoAdapter
    var toDoList = ArrayList<ToDoItem>()

    var called = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!called) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

//            val signInActivity = Intent(this, GoogleSignInActivity::class.java)
//            startActivityForResult(signInActivity, 0)
            userId = intent.getStringExtra("id")

            val db = Firebase.firestore
            db.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                        val keys = document.data?.keys
                        if (keys != null) {
                            for (key in keys) {
                                val toDoItem = document.data!!.get(key) as? HashMap<String, Any?>
                                toDoList.add(ToDoItem(key, toDoItem))
                            }
                        }
                        Log.d("TAG", "${document.id} => $toDoList")
                        userId = document.id
                        userData = document.data as Map<String, Any>
                        val data = hashMapOf("capital" to hashMapOf(
                            "asd" to true,
                            "time" to Timestamp(Date(120, 4, 6))

                        ))

                        db.collection("Users").document("DJ")
                            .set(data, SetOptions.merge())
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }

            Log.d("TAG", toDoList.toString())

            listView = findViewById(R.id.id_listView)
            bRefresh = findViewById(R.id.id_bRefresh)
            bAdd = findViewById(R.id.id_bAdd)
            etTitle = findViewById(R.id.id_etTitle)
            etDescription = findViewById(R.id.id_etDescription)
            etLocation = findViewById(R.id.id_etLocation)
            etTime = findViewById(R.id.id_etTime)

            var testList = ArrayList<ToDoItem>()
            testList.add(ToDoItem("title", "details", false, "location", Timestamp(0, 0)))
            testList.add(ToDoItem("title", "details", false, "location", Timestamp(0, 0)))
            testList.add(ToDoItem("title", "details", false, "location", Timestamp(0, 0)))

            todoAdapter = TodoAdapter(this, R.layout.todo_item, toDoList)
            listView.adapter = todoAdapter

            bRefresh.setOnClickListener {
                for (i in todoAdapter.getList().size - 1 downTo 0) {
                    if (todoAdapter.getList()[i].isCompleted) {
                        todoAdapter.removeItem(i)
                    }
                }
                todoAdapter.notifyDataSetChanged()
            }

            bAdd.setOnClickListener {
                var date = Date()
                try {
                    val formatter: DateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
                    date = formatter.parse(etTime.text.toString()) as Date
                } catch (exception: Exception) {

                }

                todoAdapter.addItem(
                    ToDoItem(
                        etTitle.text.toString(),
                        etDescription.text.toString(),
                        false,
                        etLocation.text.toString(),
                        date
                    )
                )
                todoAdapter.notifyDataSetChanged()

                etTitle.text.clear()
                etDescription.text.clear()
                etLocation.text.clear()
                etTime.text.clear()
            }
            called = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                userId = data.getStringExtra("id")
            }
        }
    }

    class TodoAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val todoItems: ArrayList<ToDoItem>):
        ArrayAdapter<ToDoItem>(context, layoutResource, todoItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createViewFromResource(position, parent)
        }

        fun addItem(item: ToDoItem) {
            todoItems.add(item)
        }

        fun removeItem(position: Int) {
            todoItems.removeAt(position)
        }

        fun getList(): ArrayList<ToDoItem> {
            return todoItems
        }

        private fun createViewFromResource(position: Int, parent: ViewGroup?): View{
            val view: View = LayoutInflater.from(context).inflate(layoutResource, parent, false)

            view.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("\n" + todoItems[position].description + "\n\n" + todoItems[position].location)
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                    }
                val alert = dialogBuilder.create()
                alert.setTitle("ADDITIONAL INFO")
                alert.show()
            }

            view.id_tTitle.text = todoItems[position].title
            view.id_tTime.text = todoItems[position].time.toString()

            val checkBox: CheckBox = view.id_checkBox
            checkBox.isChecked = todoItems[position].isCompleted
            view.id_checkBox.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
                todoItems[position].isCompleted = b
            }

            return view
        }
    }
}