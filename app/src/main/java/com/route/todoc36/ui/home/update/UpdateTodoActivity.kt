package com.route.todoc36.ui.home.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.route.todoc36.R
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import com.route.todoc36.databinding.ActivityUpdateTodoBinding
import java.text.SimpleDateFormat
import java.util.*

class UpdateTodoActivity : AppCompatActivity() {

    lateinit var activityUpdateTodoBinding: ActivityUpdateTodoBinding
    lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityUpdateTodoBinding=ActivityUpdateTodoBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_update_todo)
        //recive intent
        task= (intent.getSerializableExtra("todo") as? Task)!!
       // Log.e(task.title,"my tiltle of task")
        //print(task.title)

        //show data
        showData(task)
        //Log.e(task.title,"my tiltle of task")

        ///click and update database
        activityUpdateTodoBinding.submit.setOnClickListener {
           updateTodo()
        }

    }

    private fun updateTodo(){
        Log.e(task.title,"my tiltle of task")

        if (isValidForm()==true){
            //get data
            task!!.title=activityUpdateTodoBinding.titleContainer.editText!!.text.toString()
            task!!.desc=activityUpdateTodoBinding.descContainer.editText?.text.toString()
            task!!.date=activityUpdateTodoBinding.dateContainer.editText!!.text.toString().toLong()

            ///update database
            MyDataBase.getInstance(this@UpdateTodoActivity)
                .getTasksDao()
                .updateTask(task)

        }

    }
    fun isValidForm():Boolean{
        var isValid = true;
        if(activityUpdateTodoBinding.titleContainer.editText?.text.toString().isNullOrBlank()){
            activityUpdateTodoBinding.titleContainer.error = getString(R.string.please_enter_title);
            isValid = false;
        }else {
            activityUpdateTodoBinding.titleContainer.error=null
        }

        if(activityUpdateTodoBinding.descContainer.editText?.text.toString().isNullOrBlank()){
            activityUpdateTodoBinding.descContainer.error = getString(R.string.please_enter_desc);
            isValid = false;
        }else {
            activityUpdateTodoBinding.descContainer.error=null
        }

        if(activityUpdateTodoBinding.date.text.isNullOrBlank()){
            activityUpdateTodoBinding.dateContainer.error = getString(R.string.please_select_date);
            isValid = false;
        }else {
            activityUpdateTodoBinding.dateContainer.error=null
        }

        return isValid;
    }


    private fun showData(task: Task) {

        activityUpdateTodoBinding.titleContainer.editText?.setText(task.title)
       // Log.e(task.title,"my tiltle of task")
        activityUpdateTodoBinding.descContainer.editText!!.setText(task.desc)
        val date=convertLongToTime(task.date!!)
        activityUpdateTodoBinding.date.setText(date)

    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

}