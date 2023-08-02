package com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DataSignup
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry

class Appviewmodel(val repostry: Repostry): ViewModel(){

    // data for currency
     fun insert(dataSignup: DataSignup){
        repostry.insert(dataSignup)
    }
    fun getData():LiveData<List<DataSignup>>{
        return repostry.getData()
    }
    fun update(dataSignup: DataSignup){
        repostry.update(dataSignup)
    }

    // data for M
    fun insertM(model: ModelM){
        repostry.insertM(model)
    }
    fun updateM(model: ModelM){
        repostry.updateM(model)
    }
    fun getDataM():LiveData<List<ModelM>>{
        return repostry.getDataM()
    }

    fun getMOnth(year:String): LiveData<List<ModelM>>{
        return repostry.getMOnth(year)
    }
    fun getDataDily(day:String):LiveData<List<ModelM>>{
        return repostry.getDataDily(day)
    }

}