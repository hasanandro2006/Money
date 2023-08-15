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





    @Query("select * from mainTable order by id asc")
    fun getDataM():LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE dateMonth= :year")
    fun getDataMonth(year:String):LiveData<List<ModelM>>

    @Query("select * from mainTable where year=:Year")
    fun getDataYear(Year: String):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE date= :day")
    fun getDataDily(day:String):LiveData<List<ModelM>>

    @Query("SELECT * FROM mainTable WHERE  weekNumber =:seekNumber")
    fun getDataBetweenDates(seekNumber:Int): LiveData<List<ModelM>>

    @Query("select * from mainTable where id=:Id")
    fun getIdData(Id:Int):LiveData<List<ModelM>>
    @Query("delete  from mainTable where id=:Id")
    fun deletDataId(Id:Int)

    @Query("update mainTable set type= :Type, catagory=:Catagory, account=:Account,date=:Datee,amount=:Amount,dateMonth=:DateMOnth,note=:Note,year=:Year where id=:Id")
    fun updataAllData(Type:String,Catagory:String,Account:String,Datee:String,Amount:Double,DateMOnth:String,Id:Int,Note:String,Year:String)



}


