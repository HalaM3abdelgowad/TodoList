package com.route.todoc36.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Task (
        @ColumnInfo
        @PrimaryKey(autoGenerate = true)
        var id:Int?=null,
        @ColumnInfo
        var title:String?=null,
        @ColumnInfo
        var desc:String?=null,
        @ColumnInfo
        var date:Long?=null,
        @ColumnInfo
        var isDone:Boolean?=null
        )