package com.example.todolistapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
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

    lateinit var userId: String
    lateinit var userData: Map<String, Any>

    lateinit var todoAdapter: TodoAdapter
    var toDoList = ArrayList<ToDoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                            userData = document.data as Map<String, Any>
                            todoAdapter.notifyDataSetChanged()
                        } else {
                            val data = HashMap<String, HashMap<String, Any>>()
                            db.collection("Users").document(userId).set(data, SetOptions.merge())
                        }
                        Log.d("TAG", "${document.id} => $toDoList")

                    }
                }
                .addOnFailureListener { exception ->
                    db.collection("Users").document(userId)
                    Log.w("TAG", "Error getting documents.", exception)
                }

            Log.d("TAG List", toDoList.toString())

            listView = findViewById(R.id.id_listView)
            bRefresh = findViewById(R.id.id_bRefresh)
            bAdd = findViewById(R.id.id_bAdd)
            etTitle = findViewById(R.id.id_etTitle)
            etDescription = findViewById(R.id.id_etDescription)
            etLocation = findViewById(R.id.id_etLocation)
            etTime = findViewById(R.id.id_etTime)

        todoAdapter = TodoAdapter(this, R.layout.todo_item, toDoList)
        listView.adapter = todoAdapter

        bRefresh.setOnClickListener {
            val docRef = db.collection("Users").document(userId)
            for (i in todoAdapter.getList().size - 1 downTo 0) {
                if (todoAdapter.getList()[i].isCompleted) {
                    val updates = hashMapOf<String, Any>(
                        todoAdapter.getList()[i].title to FieldValue.delete()
                    )
                    docRef.update(updates).addOnCompleteListener { }
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
            db.collection("Users").document(userId)
                .set(ToDoItem(
                    etTitle.text.toString(),
                    etDescription.text.toString(),
                    false,
                    etLocation.text.toString(),
                    date
                ).getHashMapOf(), SetOptions.merge())
            todoAdapter.notifyDataSetChanged()

            etTitle.text.clear()
            etDescription.text.clear()
            etLocation.text.clear()
            etTime.text.clear()

        }
    }

    class TodoAdapter(
        context: Context,
        @LayoutRes private val layoutResource: Int,
        private val todoItems: ArrayList<ToDoItem>
    ) :
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

        private fun createViewFromResource(position: Int, parent: ViewGroup?): View {
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