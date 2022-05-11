package com.example.todolistapp.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.*
import com.example.todolistapp.Database.TaskViewModel

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class TaskFragment : Fragment() , taskClickChangeStatusInterface ,taskClickDeleteInterface, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    lateinit var taskAdapter: TaskAdapter
    lateinit var viewModel: TaskViewModel
    lateinit var taskRecycleView: RecyclerView
    lateinit var floatingButton: FloatingActionButton
    lateinit var filterButton: TextView
    var dateData:String = ""
    lateinit var dialog: BottomSheetDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskRecycleView = view.findViewById(R.id.recycleViewTasks)
        floatingButton = view.findViewById(R.id.floatingButton)
        filterButton = view.findViewById(R.id.filterText)
        taskRecycleView.layoutManager = LinearLayoutManager(view.context)


        taskAdapter = TaskAdapter(view.context,this,this)
        taskRecycleView.adapter = taskAdapter
        innitViewModel(filterButton)
        floatingButton.setOnClickListener{
            showDialogOne(view)
        }
        filterButton.setOnClickListener{
            viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(
                this.requireActivity().application)).get(TaskViewModel::class.java)
            viewModel.allTask.observe(this.viewLifecycleOwner) { list ->
                list?.let {
                    taskAdapter.updateList(it)
                }
            }
            filterButton.text = "No Filter"
        }
    }

    private fun innitViewModel(filterButton: TextView) {
        val Filter = arguments?.getString("filter","Default")
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(
            this.requireActivity().application)).get(TaskViewModel::class.java)
        if(Filter == "personal"){
            filterButton.text = "Personal"
            viewModel.allTasksPersonal().observe(this.viewLifecycleOwner) { list ->
                list?.let {
                    taskAdapter.updateList(it)
                }
            }
        }
        else if(Filter == "work"){
            filterButton.text = "Work"
            viewModel.allTasksWork().observe(this.viewLifecycleOwner) { list ->
                list?.let {
                    taskAdapter.updateList(it)
                }
            }
        }
        else if(Filter == "school"){
            filterButton.text = "School"
            viewModel.allTasksSchool().observe(this.viewLifecycleOwner) { list ->
                list?.let {
                    taskAdapter.updateList(it)
                }
            }
        }
        else {
            filterButton.text = "No Filter"
            viewModel.allTask.observe(this.viewLifecycleOwner) { list ->
                list?.let {
                    taskAdapter.updateList(it)
                }
            }
        }
    }


    private fun showDialogOne(view: View) {

        dialog = BottomSheetDialog(view.context)
        dialog.setContentView(R.layout.bottom_sheet_add_task)
        val btnEdit: Button? = dialog.findViewById(R.id.addTask123)
        val addTaskDeadline: TextView? = dialog.findViewById(R.id.addTaskDeadline)
        addTaskDeadline?.setOnClickListener{
            val calendar: Calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)
            TimePickerDialog(requireActivity(),this,hour,minute,true).show()
            DatePickerDialog(requireActivity(),this,year,month,day).show()
        }
        btnEdit?.setOnClickListener {
            //Toast.makeText(view.context, "Clicked on Edit", Toast.LENGTH_SHORT).show()
            val addTaskName: EditText? = dialog.findViewById(R.id.addTaskName)
            val addTaskDescription: EditText? = dialog.findViewById(R.id.addTaskDescription)
            val TaskType: Spinner? = dialog.findViewById(R.id.taskType)
            val TaskStatus: Spinner? = dialog.findViewById(R.id.taskStatus)
            val task1 = TaskModel(
                addTaskName?.text.toString(),
                dateData,
                addTaskDescription?.text.toString(),
                TaskType?.selectedItem.toString(),
                TaskStatus?.selectedItem.toString())
            if(addTaskName?.text.toString().isEmpty()||dateData.isEmpty()||addTaskDescription?.text.toString().isEmpty()){
                Toast.makeText(view.context, "Please try again", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.addTask(task1)
                dialog.closeOptionsMenu()
                dialog.hide()
                dateData=""
            }

        }
        dialog.show()
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

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val month = p2+1
        dateData="$p1/$month/$p3"
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        dateData= "$dateData $p1:$p2"
        val addTaskDeadline: TextView? = dialog.findViewById(R.id.addTaskDeadline)
        addTaskDeadline?.setText(dateData)
    }


}


