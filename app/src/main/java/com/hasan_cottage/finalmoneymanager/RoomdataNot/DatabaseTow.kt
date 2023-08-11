package com.hasan_cottage.finalmoneymanager.RoomdataNot


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataSignup::class], version = 1)
abstract class DatabaseTow:RoomDatabase() {

    abstract fun getAllDaoTow(): DatabaseDaoTow// call dao


    companion object{
        private var INSTANCE: DatabaseTow?=null

        fun getInstanceAllTow(contex:Context): DatabaseTow { // call instance database
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE =Room.databaseBuilder(
                        contex.applicationContext,
                        DatabaseTow::class.java,
                        "nameDatabase"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }


    }
}