package com.hasan_cottage.finalmoneymanager.viewModelClass

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository

class AppViewModel(private val repository: Repository): ViewModel(){


    // data for M
     fun insertM(model: ModelM){
        repository.insertM(model)
    }
    fun deleteAllItems(){
        repository.deleteAllItems()
    }

    fun getIdData(id:Int,id1: Int):LiveData<List<ModelM>>{
        return repository.getIdData(id,id1)
    }
    fun deleteDataId(id:Int){
        return repository.deleteDataId(id)
    }
    fun getDataM(id: Int):LiveData<List<ModelM>>{
        return repository.getDataM(id)
    }

    fun getMonth(year:String,id: Int): LiveData<List<ModelM>>{
        return repository.getMonth(year,id)
    }
    fun getDataDaily(day:String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataDaily(day,id)
    }
    fun getDataDailyT(day: String, accountId: Int): LiveData<List<ModelM>>{
        return repository.getDataDailyT(day,accountId)
    }
    fun getDataCalender(day:String,category:String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataCalender(day,category,id)
    }
    fun getDataCalenderCategory(category:String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataCalenderCategory(category,id)
    }
    fun getDataBetweenDates(seekNumber:Int,id1: Int): LiveData<List<ModelM>>{
        return repository.getDataBetweenDates(seekNumber,id1)
    }
    fun getDataYear(year: String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataYear(year,id)
    }
    fun updateAllData(type:String, category:String, account:String, date:String, amount:Long, dateMonth:String, id:Int, note:String, year:String){
        return repository.updateAllData(type,category,account,date,amount,dateMonth,id,note,year)
    }


    fun getDataDaily2(day:String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataDaily2(day,category,typeC,id)
    }

    fun getDataMonth2(year:String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataMonth2(year,category,typeC,id)
    }
    fun getDataBetweenDates2(seekNumber:Int,category: String,typeC: String,id: Int): LiveData<List<ModelM>>{
        return repository.getDataBetweenDates2(seekNumber,category,typeC,id)
    }
    fun getDataYear2(yearC: String,category: String,typeC: String,id: Int):LiveData<List<ModelM>>{
        return repository.getDataYear2(yearC,category,typeC,id)
    }
    fun getDataM2(category: String):LiveData<List<ModelM>>{
        return repository.getDataM2(category)
    }
}