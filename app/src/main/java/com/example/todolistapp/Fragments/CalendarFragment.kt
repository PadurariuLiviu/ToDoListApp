package com.example.todolistapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.*
import com.example.todolistapp.Database.TaskViewModel


class CalendarFragment : Fragment() , taskClickChangeStatusInterface, taskClickDeleteInterface {

    lateinit var taskAdapter: TaskAdapter
    lateinit var calendarRecycleView: RecyclerView
    lateinit var viewModel: TaskViewModel
    lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarRecycleView = view.findViewById(R.id.recycleViewCalendar)
        calendarRecycleView.layoutManager = LinearLayoutManager(view.context)

        taskAdapter = TaskAdapter(view.context,this,this)
        calendarRecycleView.adapter = taskAdapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            this.requireActivity().application)).get(TaskViewModel::class.java)

        calendarView = view.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener{
            calendarView, y, m, d ->
            var year = y
            var month = m+1
            var day = d
            var deadlineFormat = year.toString()+"/"+month.toString()+"/"+day.toString()
            Toast.makeText(view?.context, deadlineFormat, Toast.LENGTH_SHORT).show()
            viewModel.allTasksbyDate(deadlineFormat).observe(this.viewLifecycleOwner) { list ->
                list?.let {
                    taskAdapter.updateList(it)
                }
            }
        }

    }

    override fun onChangeStatusIconClick(task: TaskModel) {
        if(task.status=="To Do"){
            task.status="In Progress"
            viewModel.updateTask(task)
            Toast.makeText(view?.context, task.name+" Updated", Toast.LENGTH_SHORT).show()
        }else if(task.status=="In Progress"){
            task.status="Done"
            viewModel.updateTask(task)
            Toast.makeText(view?.context, task.name+" Updated", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeleteIconClick(task: TaskModel) {
        viewModel.deleteTask(task)
        Toast.makeText(view?.context, task.name+" Deleted", Toast.LENGTH_SHORT).show()
    }

}