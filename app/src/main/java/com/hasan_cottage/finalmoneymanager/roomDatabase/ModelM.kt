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
    val amount:Long,
    val dateMonth:String,
    val note:String,
    val year:String,
    val weekNumber:Int,
    val accountId:Int

){
    @Ignore
    constructor( type: String, category: String, account: String, date:String,amount: Long,dateMonth: String,note: String,year: String,weekNumber:Int,accountId: Int)
            : this(0,type,category,account,date,amount,dateMonth,note,year,weekNumber,accountId)
}