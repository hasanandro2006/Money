package com.hasan_cottage.finalmoneymanager.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ModelM::class], version = 37)
abstract class DatabaseAll : RoomDatabase() {

    abstract fun getAllDaoM(): DatabaseDaoM

    companion object {

        @Volatile
        private var INSTANCE: DatabaseAll? = null
        fun getInstanceAll(context: Context): DatabaseAll {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        DatabaseAll::class.java,
                        "databaseName"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE!!
        }
    }
}
