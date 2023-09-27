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
    fun getIdData(id:Int,id1: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getIdData(id,id1)
    }
    fun deleteDataId(id:Int){
        return databaseDaoM.deleteDataId(id)
    }
    fun getDataM(id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataM(id)
    }

    fun getMonth(year:String,id: Int): LiveData<List<ModelM>>{
        return databaseDaoM.getDataMonth(year,id)
    }

    fun getDataDaily(day:String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataDaily(day,id)
    }
    fun getDataDailyT(day: String, accountId: Int): LiveData<List<ModelM>>{
        return databaseDaoM.getDataDailyT(day,accountId)
    }
    fun getDataCalender(day:String,category:String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataCalender(day,category,id)
    }
    fun getDataCalenderCategory(category:String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataCalenderCategory(category,id)
    }
    fun getDataBetweenDates(seekNumber:Int,id1: Int): LiveData<List<ModelM>>{
        return databaseDaoM.getDataBetweenDates(seekNumber,id1)
    }
    fun getDataYear(year: String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataYear(year,id)
    }

    fun updateAllData(type:String, category:String, account:String, date:String, amount:Long, dateMonth:String, id:Int, note:String, year:String){
        return databaseDaoM.updateAllData(type,category,account,date,amount,dateMonth,id,note,year)
    }


    fun getDataDaily2(day:String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataDaily2(day,category,typeC,id)
    }

    fun getDataMonth2(year:String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataMonth2(year,category,typeC,id)
    }
    fun getDataBetweenDates2(seekNumber:Int,category: String,typeC: String,id: Int): LiveData<List<ModelM>>{
        return databaseDaoM.getDataBetweenDates2(seekNumber,category,typeC,id)
    }
    fun getDataYear2(yearC: String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>{
        return databaseDaoM.getDataYear2(yearC,category,typeC,id)
    }
    fun getDataM2(category: String):LiveData<List<ModelM>>{
        return databaseDaoM.getDataM2(category)
    }



}