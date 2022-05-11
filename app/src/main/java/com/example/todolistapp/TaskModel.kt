package com.example.todolistapp

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "taskTable")
data class TaskModel(
    @ColumnInfo(name = "Name") val name: String,

    @ColumnInfo(name = "Deadline") val deadline: String,

    @ColumnInfo(name = "Description") val description: String,

    @ColumnInfo(name = "Type") val type: String,

    @ColumnInfo(name = "Status") var status: String,
    ){
    @PrimaryKey(autoGenerate = true) var id =0
}
