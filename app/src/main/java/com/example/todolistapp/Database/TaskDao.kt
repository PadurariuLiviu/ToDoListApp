package com.example.todolistapp.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolistapp.TaskModel

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskModel)

    @Update
    fun updateTask(task: TaskModel)

    @Delete
    fun deleteTask(task: TaskModel)

    @Query("SELECT * FROM taskTable order by id asc")
    fun getAllTasks(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM taskTable WHERE Type = 'Personal' order by id asc")
    fun getAllTasksPersonal(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM taskTable WHERE Type = 'Work' order by id asc")
    fun getAllTasksWork(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM taskTable WHERE Type = 'School' order by id asc")
    fun getAllTasksSchool(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM taskTable WHERE Deadline like :date||'%' ")
    fun getAllTasksByDate(date: String): LiveData<List<TaskModel>>
}