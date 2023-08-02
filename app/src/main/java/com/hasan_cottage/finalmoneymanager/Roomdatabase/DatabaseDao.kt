package com.hasan_cottage.finalmoneymanager.Roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: DataSignup)

    @Update
    fun update(data: DataSignup)

    @Delete
    fun delete(data: DataSignup)

    @Query("select * from databasesignup order by id asc")
    fun getData():LiveData<List<DataSignup>>

}
@Dao
interface DatabaseDaoM{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertM(data: ModelM)

    @Update
    fun updateM(data: ModelM)

    @Delete
    fun deleteM(data: ModelM)

    @Query("select * from mainTable order by id asc")
    fun getDataM():LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE dateMonth= :year")
    fun getDataMonth(year:String):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day")
    fun getDataDily(day:String):LiveData<List<ModelM>>


}


