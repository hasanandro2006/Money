package com.hasan_cottage.finalmoneymanager.Roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataSignup::class,ModelM::class], version = 25)
abstract class DatabaseAll:RoomDatabase() {

    abstract fun getAllDao():DatabaseDao// call dao
    abstract fun getAllDaoM():DatabaseDaoM

    companion object{
        private var INSTANCE:DatabaseAll?=null

        fun getInstanceAll(contex:Context):DatabaseAll{ // call instance database
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE=Room.databaseBuilder(
                        contex,
                        DatabaseAll::class.java,
                        "allDatabase"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }


    }
}