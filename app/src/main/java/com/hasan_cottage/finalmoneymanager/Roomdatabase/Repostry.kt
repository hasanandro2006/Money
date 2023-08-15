package com.hasan_cottage.finalmoneymanager.Roomdatabase

import androidx.lifecycle.LiveData
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DataSignup

class Repostry( val databaseDaoM: DatabaseDaoM) {



    // dat for M
    fun insertM(model:ModelM){
        databaseDaoM.insertM(model)
    }
    fun updateM(model: ModelM){
        databaseDaoM.updateM(model)
    }
    fun getIdData(Id:Int):LiveData<List<ModelM>>{
        return databaseDaoM.getIdData(Id)
    }
    fun deletDataId(Id:Int){
        return databaseDaoM.deletDataId(Id)
    }
    fun getDataM():LiveData<List<ModelM>>{
        return databaseDaoM.getDataM()
    }

    fun getMOnth(year:String): LiveData<List<ModelM>>{
        return databaseDaoM.getDataMonth(year)
    }

    fun getDataDily(day:String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataDily(day)
    }
    fun getDataBetweenDates(seekNumber:Int): LiveData<List<ModelM>>{
        return databaseDaoM.getDataBetweenDates(seekNumber)
    }
    fun getDataYear(Year: String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataYear(Year)
    }

    fun updataAllData(Type:String,Catagory:String,Account:String,Datee:String,Amount:Double,DateMOnth:String,Id:Int,Note:String,Year:String){
        return databaseDaoM.updataAllData(Type,Catagory,Account,Datee,Amount,DateMOnth,Id,Note,Year)
    }





}