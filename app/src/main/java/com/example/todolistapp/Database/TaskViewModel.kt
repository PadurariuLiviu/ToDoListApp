package com.example.todolistapp.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) :AndroidViewModel(application){
    var allTask: LiveData<List<TaskModel>>
    val repository: TaskRepository


    init {
        val dao = TaskDatabase.getDatabase(application).getTaskDao()
        repository = TaskRepository(dao)
        allTask = repository.allTask
    }

    fun deleteTask(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun updateTask(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun addTask(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }
    fun allTasksPersonal(): LiveData<List<TaskModel>> {
        return repository.AllTasksPersonal()
    }
    fun allTasksWork(): LiveData<List<TaskModel>> {
        return repository.AllTasksWork()
    }
    fun allTasksSchool(): LiveData<List<TaskModel>> {
        return repository.AllTasksSchool()
    }
    fun allTasksbyDate(date: String): LiveData<List<TaskModel>> {
        return repository.AllTasksbyDate(date)
    }

}