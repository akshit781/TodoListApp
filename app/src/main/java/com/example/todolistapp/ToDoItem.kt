package com.example.todolistapp

import java.util.Date
import com.google.firebase.Timestamp

class ToDoItem(
    var title: String,
    var description: String,
    var isCompleted: Boolean,
    var location: String,
    var time: Date
) {

    constructor(title : String, description: String, isCompleted: Boolean, location: String, time : Timestamp) : this(
        title,
        description,
        isCompleted,
        location,
        time.toDate()
    )

    constructor(title: String, details: HashMap<String, Any?>?) : this(
        title,
        details?.get("description") as String,
        details?.get("isCompleted") as Boolean,
        details?.get("location") as String,
        details?.get("time") as Timestamp
    )

    fun getHashMapOf(): HashMap<String, HashMap<String, Any>> {
        val map: HashMap<String, HashMap<String, Any>> = hashMapOf(
            title to hashMapOf(
                "description" to description,
                "isCompleted" to isCompleted,
                "location" to location,
                "time" to time
            )
        )
        return map
    }
}