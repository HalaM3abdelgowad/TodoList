package com.route.todoc36.database.dao

import androidx.room.*
import com.route.todoc36.database.Task

@Dao
interface TaskDao {

    @Insert
    fun insertTask(task:Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from Task")
    fun selectAllTasks():List<Task>

    @Query("select * from Task where date = :dateParam")
    fun selectTasksByDate(dateParam:Long):List<Task>

    @Query("select * from Task where id = :id")
    fun getTaskById(id:Int):Task
}