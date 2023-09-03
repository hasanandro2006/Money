package com.hasan_cottage.finalmoneymanager.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDaoM{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertM(data: ModelM)

    @Update
    fun updateM(data: ModelM)

    @Delete
     fun deleteM(data: ModelM)

    @Query("DELETE FROM mainTable")
    fun deleteAllItems()


    @Query("select * from mainTable order by date asc")
    fun getDataM():LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE dateMonth= :year")
    fun getDataMonth(year:String):LiveData<List<ModelM>>

    @Query("select * from mainTable where year=:yearC")
    fun getDataYear(yearC: String):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day")
      fun getDataDaily(day:String):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day and category= :category")
    fun getDataCalender(day:String,category:String):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE  category= :category")
    fun getDataCalenderCategory(category:String):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE  weekNumber =:seekNumber")
    fun getDataBetweenDates(seekNumber:Int): LiveData<List<ModelM>>

    @Query("select * from mainTable where id=:idC")
    fun getIdData(idC:Int):LiveData<List<ModelM>>
    @Query("delete  from mainTable where id=:idD")
    fun deleteDataId(idD:Int)

    @Query("update mainTable set type= :typeC, category=:categoryC, account=:account,date=:date,amount=:amount,dateMonth=:dateMonth,note=:note,year=:year where id=:id")
    fun updateAllData(typeC:String, categoryC:String, account:String, date:String, amount:Double, dateMonth:String, id:Int, note:String, year:String)



    @Query("SELECT * FROM mainTable WHERE date= :day and category=:category and type=:typeC")
    fun getDataDaily2(day:String,category: String,typeC: String):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE dateMonth= :year and category=:category and type=:typeC")
    fun getDataMonth2(year:String,category: String,typeC: String):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE  weekNumber =:seekNumber and category=:category and type=:typeC")
    fun getDataBetweenDates2(seekNumber:Int,category: String,typeC: String): LiveData<List<ModelM>>
    @Query("select * from mainTable where year=:yearC and category=:category and type=:typeC")
    fun getDataYear2(yearC: String,category: String,typeC: String):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE category = :category ORDER BY date ASC")
    fun getDataM2(category: String):LiveData<List<ModelM>>

}


