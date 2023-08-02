package com.hasan_cottage.finalmoneymanager.Roomdatabase

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "databasesignup")
data class DataSignup(
    @PrimaryKey(autoGenerate = true)
    val id:Int=1,
    val currencyName:String,
    val currencyCode:String,
    val currencySymbol:String,
    val name:String,
    var position:Int

    ){
    @Ignore
    constructor(currencyName:String, currencyCode:String, currencySymbol:String,name: String,position: Int):
            this(0,currencyName, currencyCode, currencySymbol,name,position)

}