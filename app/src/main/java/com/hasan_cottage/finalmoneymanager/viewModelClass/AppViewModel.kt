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
    fun getDataBetweenDates(seekNumber:Int): LiveData<List<ModelM>>{
        return repository.getDataBetweenDates(seekNumber)
    }
    fun getDataYear(year: String):LiveData<List<ModelM>>{
        return repository.getDataYear(year)
    }
    fun updateAllData(type:String, category:String, account:String, date:String, amount:Double, dateMonth:String, id:Int, note:String, year:String){
        return repository.updateAllData(type,category,account,date,amount,dateMonth,id,note,year)
    }

}