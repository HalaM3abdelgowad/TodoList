package com.route.todoc36.ui.home.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoc36.R
import com.route.todoc36.base.BaseBottomSheet
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import com.route.todoc36.databinding.FragmentAddTaskBinding
import java.util.*

class AddTaskBottomSheet :BaseBottomSheet() {

    lateinit var viewBinding:FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAddTaskBinding.inflate(inflater,
        container,false);
        return viewBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.submit.setOnClickListener{
            addTask()
        }
        viewBinding.dateContainer.setOnClickListener{
            showDatePicker();
        }
    }
    val currentDate = Calendar.getInstance();
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }
    fun showDatePicker(){
        val dialoge = DatePickerDialog(
            requireContext() ,object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    currentDate.set(Calendar.YEAR,year);
                    currentDate.set(Calendar.MONTH,month)
                    currentDate.set(Calendar.DAY_OF_MONTH,day)
                    viewBinding.date.setText("$day / ${month+1} / $year")
                }
            },currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
        currentDate.get(Calendar.DAY_OF_MONTH))
        dialoge.show()
    }
    fun addTask(){
        if(!isValidForm())
            return;
        showLoadingDialog();

        val task = Task(
            title = viewBinding.titleContainer.editText?.text.toString(),
            desc = viewBinding.descContainer.editText?.text.toString(),
            date = currentDate.timeInMillis,
            isDone = false
        )
        MyDataBase.getInstance(requireContext())
            .getTasksDao()
            .insertTask(task)
        hideLoading()
        showMessage("Task Added Successfully",
        posActionTitle = "ok",posAction = { dialogInterface, i ->
                dialogInterface.dismiss()
                dismiss()
                taskAddedLister?.onTaskAdded()
            },cancelable = false);
    }
    fun isValidForm():Boolean{
        var isValid = true;
        if(viewBinding.titleContainer.editText?.text.toString().isNullOrBlank()){
            viewBinding.titleContainer.error = getString(R.string.please_enter_title);
            isValid = false;
        }else {
            viewBinding.titleContainer.error=null
        }

        if(viewBinding.descContainer.editText?.text.toString().isNullOrBlank()){
            viewBinding.descContainer.error = getString(R.string.please_enter_desc);
            isValid = false;
        }else {
            viewBinding.descContainer.error=null
        }

        if(viewBinding.date.text.isNullOrBlank()){
            viewBinding.dateContainer.error = getString(R.string.please_select_date);
            isValid = false;
        }else {
            viewBinding.dateContainer.error=null
        }

        return isValid;
    }

    var taskAddedLister : OnTaskAddedListener?=null
    interface OnTaskAddedListener{
        fun onTaskAdded();
    }
}