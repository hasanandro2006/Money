package com.hasan_cottage.finalmoneymanager.roomDatabaseNot

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "dataBaseSing")
data class DataSignup(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,

    val currencyName:String,
    val currencyCode:String,
    val currencySymbol:String,
    val name:String,


    ){
    @Ignore
    constructor(currencyName:String, currencyCode:String, currencySymbol:String,name: String):
            this(0,currencyName, currencyCode, currencySymbol,name)

}