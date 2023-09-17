package com.hasan_cottage.finalmoneymanager.roomDatabaseNot

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM

@Dao
interface DatabaseDaoTow {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DataSignup)

    @Update
    fun update(data: DataSignup)

    @Delete
    fun delete(data: DataSignup)

    @Query("select * from dataBaseSing WHERE id= :idd")
    fun getDataId(idd:Int): LiveData<MutableList<DataSignup>>
    @Query("select * from dataBaseSing order by id asc")
    fun getData():LiveData<MutableList<DataSignup>>
    @Query("delete from dataBaseSing where id=:id")
    fun deleteId(id:Int)
}