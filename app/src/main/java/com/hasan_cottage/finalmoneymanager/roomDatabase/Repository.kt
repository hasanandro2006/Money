package com.hasan_cottage.finalmoneymanager.roomDatabase

import androidx.lifecycle.LiveData

class Repository(private val databaseDaoM: DatabaseDaoM) {

    // dat for M
     fun insertM(model:ModelM){
        databaseDaoM.insertM(model)
    }
    fun deleteAllItems(){
        databaseDaoM.deleteAllItems()
    }
    fun updateM(model: ModelM){
        databaseDaoM.updateM(model)
    }
    fun getIdData(id:Int):LiveData<List<ModelM>>{
        return databaseDaoM.getIdData(id)
    }
    fun deleteDataId(id:Int){
        return databaseDaoM.deleteDataId(id)
    }
    fun getDataM():LiveData<List<ModelM>>{
        return databaseDaoM.getDataM()
    }

    fun getMonth(year:String): LiveData<List<ModelM>>{
        return databaseDaoM.getDataMonth(year)
    }

    fun getDataDaily(day:String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataDaily(day)
    }
    fun getDataCalender(day:String,category:String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataCalender(day,category)
    }
    fun getDataCalenderCategory(category:String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataCalenderCategory(category)
    }
    fun getDataBetweenDates(seekNumber:Int): LiveData<List<ModelM>>{
        return databaseDaoM.getDataBetweenDates(seekNumber)
    }
    fun getDataYear(year: String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataYear(year)
    }

    fun updateAllData(type:String, category:String, account:String, date:String, amount:Double, dateMonth:String, id:Int, note:String, year:String){
        return databaseDaoM.updateAllData(type,category,account,date,amount,dateMonth,id,note,year)
    }





}