package com.example.todolistapp.Database

import androidx.lifecycle.LiveData
import com.example.todolistapp.TaskModel

class TaskRepository(private val taskDao: TaskDao) {

    val allTask: LiveData<List<TaskModel>> = taskDao.getAllTasks()

    suspend fun insert(task: TaskModel){
        taskDao.insertTask(task)
    }

    suspend fun delete(task: TaskModel){
        taskDao.deleteTask(task)
    }

    suspend fun update(task: TaskModel){
        taskDao.updateTask(task)
    }

    fun AllTasksPersonal(): LiveData<List<TaskModel>> {
        return taskDao.getAllTasksPersonal()
    }

    fun AllTasksWork(): LiveData<List<TaskModel>> {
        return taskDao.getAllTasksWork()
    }

    fun AllTasksSchool(): LiveData<List<TaskModel>> {
        return taskDao.getAllTasksSchool()
    }
    fun AllTasksbyDate(date: String): LiveData<List<TaskModel>> {
        return taskDao.getAllTasksByDate(date)
    }


}