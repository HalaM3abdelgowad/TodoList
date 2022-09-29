package com.route.todoc36.ui.home.list

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.route.todoc36.base.BaseFragment
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import com.route.todoc36.databinding.FragmentTasksListBinding
import com.route.todoc36.ui.home.update.UpdateTodoActivity
import java.util.*

class ListTasksFragment :BaseFragment() {
    lateinit var viewBinding: FragmentTasksListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentTasksListBinding.inflate(layoutInflater,container,false);
        return viewBinding.root;
    }

    lateinit var adapter :TasksListAdapter
    lateinit var tasksList :List<Task>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksList = MyDataBase.getInstance(requireContext())
            .getTasksDao().selectTasksByDate(currentDate.timeInMillis)
        adapter = TasksListAdapter(tasksList)
        adapter.onDeleteClickListener = object :TasksListAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, item: Task) {
                deleteTask(item)
            }
        }

        /// for make edit action
        adapter.onItemClickedToBeUpdated=object :OnItemClickedToBeUpdated{
            override fun onClickToBeUpdated(task: Task) {
                showMessage("What you want to do","update",
                    DialogInterface.OnClickListener { dialogInterface, i -> updateTodo(task) }
                ,"makeDone",DialogInterface.OnClickListener { dialogInterface, i -> makeTodoDone(task) })


            }

        }

        viewBinding.recyclerView.adapter = adapter
        viewBinding.calendarView.setOnDateChangedListener { widget, selectedDate, selected ->
            // when user clicks on date
            currentDate.set(Calendar.MONTH,selectedDate.month-1)
            currentDate.set(Calendar.DAY_OF_MONTH,selectedDate.day)
            currentDate.set(Calendar.YEAR,selectedDate.year)
            reloadTasks()
        }
        viewBinding.calendarView.setDateSelected(CalendarDay.today(),true)
    }

    private fun updateTodo(task: Task) {

        val intent :Intent=Intent(requireContext(),UpdateTodoActivity::class.java)
        intent.putExtra("todo",task)
        startActivity(intent)
    }

    private fun makeTodoDone(task: Task) {

        task.isDone=true
        MyDataBase.getInstance(requireContext()).getTasksDao().updateTask(task)
        refreshRecyclerItems()

    }

    private fun refreshRecyclerItems() {
        adapter.reloadTasks(MyDataBase.getInstance(requireContext()).getTasksDao().selectAllTasks())
        adapter.notifyDataSetChanged()
    }

    val currentDate = Calendar.getInstance();
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }
    override fun onResume() {
        super.onResume()
        reloadTasks()
    }
    fun reloadTasks(){
        tasksList = MyDataBase.getInstance(requireContext())
            .getTasksDao()
            .selectTasksByDate(currentDate.timeInMillis);
        adapter.reloadTasks(tasksList)
    }
    fun deleteTask(task:Task){

        showMessage("Are you sure you want to delete this task ?",
            posActionTitle = "yes",
            posAction = { dialogInterface, i ->
                dialogInterface?.dismiss()
                MyDataBase.getInstance(requireContext())
                    .getTasksDao()
                    .deleteTask(task)
                reloadTasks() },
            negActionTitle = "cancel",
            negAction = DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface?.dismiss()
            }
        )



    }
    companion object{
        val TAG = "Tasks-Fragment"
    }
}