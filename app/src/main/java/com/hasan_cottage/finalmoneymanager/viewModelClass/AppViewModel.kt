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

    fun getIdData(id:Int):LiveData<List<ModelM>>{
        return repository.getIdData(id)
    }
    fun deleteDataId(id:Int){
        return repository.deleteDataId(id)
    }
    fun getDataM():LiveData<List<ModelM>>{
        return repository.getDataM()
    }

    fun getMonth(year:String): LiveData<List<ModelM>>{
        return repository.getMonth(year)
    }
    fun getDataDaily(day:String):LiveData<List<ModelM>>{
        return repository.getDataDaily(day)
    }
    fun getDataCalender(day:String,category:String):LiveData<List<ModelM>>{
        return repository.getDataCalender(day,category)
    }
    fun getDataCalenderCategory(category:String):LiveData<List<ModelM>>{
        return repository.getDataCalenderCategory(category)
    }
    fun getDataBetweenDates(seekNumber:Int): LiveData<List<ModelM>>{
        return repository.getDataBetweenDates(seekNumber)
    }
    fun getDataYear(year: String):LiveData<List<ModelM>>{
        return repository.getDataYear(year)
    }
    fun updateAllData(type:String, category:String, account:String, date:String, amount:Double, dateMonth:String, id:Int, note:String, year:String){
        return repository.updateAllData(type,category,account,date,amount,dateMonth,id,note,year)
    }


    fun getDataDaily2(day:String,category: String,typeC: String):LiveData<List<ModelM>>{
        return repository.getDataDaily2(day,category,typeC)
    }

    fun getDataMonth2(year:String,category: String,typeC: String):LiveData<List<ModelM>>{
        return repository.getDataMonth2(year,category,typeC)
    }
    fun getDataBetweenDates2(seekNumber:Int,category: String,typeC: String): LiveData<List<ModelM>>{
        return repository.getDataBetweenDates2(seekNumber,category,typeC)
    }
    fun getDataYear2(yearC: String,category: String,typeC: String):LiveData<List<ModelM>>{
        return repository.getDataYear2(yearC,category,typeC)
    }
    fun getDataM2(category: String):LiveData<List<ModelM>>{
        return repository.getDataM2(category)
    }
}