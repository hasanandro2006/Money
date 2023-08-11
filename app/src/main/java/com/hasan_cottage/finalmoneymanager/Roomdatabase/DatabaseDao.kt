package com.hasan_cottage.finalmoneymanager.Roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DataSignup

@Dao
interface DatabaseDaoM{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertM(data: ModelM)

    @Update
    fun updateM(data: ModelM)

    @Delete
    fun deleteM(data: ModelM)

    @Query("select * from mainTable where id=:Id")
    fun getIdData(Id:Int):LiveData<List<ModelM>>

    @Query("select * from mainTable order by id asc")
    fun getDataM():LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE dateMonth= :year")
    fun getDataMonth(year:String):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day")
    fun getDataDily(day:String):LiveData<List<ModelM>>




}


