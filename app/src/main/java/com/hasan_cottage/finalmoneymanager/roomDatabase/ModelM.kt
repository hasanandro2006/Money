package com.hasan_cottage.finalmoneymanager.roomDatabase

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "mainTable")
data class ModelM(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val type:String,
    val category:String,
    val account:String,
    val date:String,
    val amount:Double,
    val dateMonth:String,
    val note:String,
    val year:String,
    val weekNumber:Int

){
    @Ignore
    constructor( type: String, category: String, account: String, date:String,amount: Double,dateMonth: String,note: String,year: String,weekNumber:Int)
            : this(0,type,category,account,date,amount,dateMonth,note,year,weekNumber)
}