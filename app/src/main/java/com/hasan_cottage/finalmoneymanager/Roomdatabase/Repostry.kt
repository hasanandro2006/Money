package com.hasan_cottage.finalmoneymanager.Roomdatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Repostry(val databaseDao: DatabaseDao, val databaseDaoM: DatabaseDaoM) {

    // data for currency
     fun insert(model:DataSignup){
        databaseDao.insert(model)
    }
    fun update(model: DataSignup){
        databaseDao.update(model)
    }
    fun getData():LiveData<List<DataSignup>>{
        return databaseDao.getData()
    }


    // dat for M
    fun insertM(model:ModelM){
        databaseDaoM.insertM(model)
    }
    fun updateM(model: ModelM){
        databaseDaoM.updateM(model)
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






}