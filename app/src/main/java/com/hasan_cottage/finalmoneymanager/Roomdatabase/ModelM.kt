package com.hasan_cottage.finalmoneymanager.Roomdatabase

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "mainTable")
data class ModelM(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val type:String,
    val catagory:String,
    val account:String,
    val date:String,
    val amount:Double,
    val dateMonth:String,
    val note:String,
    val year:String
){
    @Ignore
    constructor( type: String, catagor: String, account: String, date:String,amount: Double,dateMonth: String,note: String,year: String)
            : this(0,type,catagor,account,date,amount,dateMonth,note,year)
}