package com.route.todoc36.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.route.todoc36.R
import com.route.todoc36.database.MyDataBase
import com.route.todoc36.database.Task
import com.route.todoc36.databinding.ActivityMainBinding
import com.route.todoc36.ui.home.add.AddTaskBottomSheet
import com.route.todoc36.ui.home.list.ListTasksFragment
import com.route.todoc36.ui.home.settings.SettingsFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNavigationView.setOnItemSelectedListener{
            val id  = it.itemId;
            when(id){
                R.id.navigation_tasks_list ->{
                    showFragment(ListTasksFragment())
               }
                R.id.navigation_settings->{
                    showFragment(SettingsFragment())
                }
            }
            true
//            return@setOnItemSelectedListener
        }
        viewBinding.bottomNavigationView.selectedItemId = R.id.navigation_tasks_list
        viewBinding.addButton.setOnClickListener{
            showAddTaskSheet();
        }
    }

    fun showAddTaskSheet(){
        val bottomSheet =AddTaskBottomSheet()
        bottomSheet.taskAddedLister =
            object :AddTaskBottomSheet.OnTaskAddedListener{
                override fun onTaskAdded() {

                   val fragment =  supportFragmentManager
                        .findFragmentByTag(ListTasksFragment.TAG)

                    if(fragment!=null){
                        (fragment as ListTasksFragment).reloadTasks()
                    }
                }
            }
        bottomSheet.show(supportFragmentManager,null)
    }
    fun showFragment(fragment:Fragment){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment,

            if (fragment is SettingsFragment)
                SettingsFragment.TAG
            else
                ListTasksFragment.TAG)
            .commit()
    }
}