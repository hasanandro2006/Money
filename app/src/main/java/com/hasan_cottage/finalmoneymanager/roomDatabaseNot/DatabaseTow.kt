package com.hasan_cottage.finalmoneymanager.roomDatabaseNot


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataSignup::class], version = 3)
abstract class DatabaseTow:RoomDatabase() {

    abstract fun getAllDaoTow(): DatabaseDaoTow// call dao


    companion object{
        private var INSTANCE: DatabaseTow?=null

        fun getInstanceAllTow(context:Context): DatabaseTow { // call instance database
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE =Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseTow::class.java,
                        "nameDatabase"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }


    }
}