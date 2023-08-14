package com.hasan_cottage.finalmoneymanager.RoomdataNot

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DataSignup

@Dao
interface DatabaseDaoTow {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DataSignup)

    @Update
    fun update(data: DataSignup)

    @Delete
    fun delete(data: DataSignup)

    @Query("select * from databasesignup order by id asc")
    fun getData(): LiveData<List<DataSignup>>
    @Query("delete from databasesignup where id=:Id")
    fun deleteId(Id:Int)
}