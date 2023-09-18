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


    @Query("select * from mainTable WHERE accountId = :id  order by date asc")
    fun getDataM(id: Int):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE dateMonth= :year and accountId=:id")
    fun getDataMonth(year:String,id:Int):LiveData<List<ModelM>>

    @Query("select * from mainTable where year=:yearC and accountId=:id")
    fun getDataYear(yearC: String,id: Int):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day and accountId=:id")
      fun getDataDaily(day:String,id: Int):LiveData<List<ModelM>>

      // optional
    @Query("SELECT * FROM mainTable WHERE date = :day AND accountId = :accountId")
    fun getDataDailyT(day: String, accountId: Int): LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day and category= :category and accountId=:id")
    fun getDataCalender(day:String,category:String,id: Int):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE  category= :category and accountId=:id")
    fun getDataCalenderCategory(category:String,id: Int):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE  weekNumber =:seekNumber and accountId=:id")
    fun getDataBetweenDates(seekNumber:Int,id:Int): LiveData<List<ModelM>>

    @Query("select * from mainTable where id=:idC and accountId=:id")
    fun getIdData(idC:Int,id:Int):LiveData<List<ModelM>>
    @Query("delete  from mainTable where id=:idD")
    fun deleteDataId(idD:Int)

    @Query("update mainTable set type= :typeC, category=:categoryC, account=:account,date=:date,amount=:amount,dateMonth=:dateMonth,note=:note,year=:year where id=:id")
    fun updateAllData(typeC:String, categoryC:String, account:String, date:String, amount:Double, dateMonth:String, id:Int, note:String, year:String)



    @Query("SELECT * FROM mainTable WHERE date= :day and category=:category and type=:typeC and accountId=:id")
    fun getDataDaily2(day:String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE dateMonth= :year and category=:category and type=:typeC and accountId=:id")
    fun getDataMonth2(year:String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE  weekNumber =:seekNumber and category=:category and type=:typeC and accountId=:id")
    fun getDataBetweenDates2(seekNumber:Int,category: String,typeC: String,id: Int): LiveData<List<ModelM>>
    @Query("select * from mainTable where year=:yearC and category=:category and type=:typeC and accountId=:id")
    fun getDataYear2(yearC: String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>
    @Query("SELECT * FROM mainTable WHERE category = :category ORDER BY date ASC")
    fun getDataM2(category: String):LiveData<List<ModelM>>

}


