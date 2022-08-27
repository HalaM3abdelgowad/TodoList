package com.route.todoc36.database

import android.content.Context
import android.graphics.BitmapFactory
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoc36.database.dao.TaskDao

@Database(entities = [Task::class],version = 2,exportSchema = false)
abstract class MyDataBase :RoomDatabase() {
    abstract fun getTasksDao():TaskDao

    companion object{
        private var myDataBase:MyDataBase?=null
        fun getInstance(context:Context):MyDataBase{
            if(myDataBase==null){
                // create object
                myDataBase = Room.databaseBuilder(
                    context,MyDataBase::class.java,
                    "tasks-database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                     .build();
            }
            return myDataBase!!;
        }
    }
}