package com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry

class Appviewmodel(val repostry: Repostry): ViewModel(){


    // data for M
    fun insertM(model: ModelM){
        repostry.insertM(model)
    }
    fun updateM(model: ModelM){
        repostry.updateM(model)
    }
    fun getIdData(Id:Int):LiveData<List<ModelM>>{
        return repostry.getIdData(Id)
    }
    fun deletDataId(Id:Int){
        return repostry.deletDataId(Id)
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
    fun getDataYear(Year: String):LiveData<List<ModelM>>{
        return repostry.getDataYear(Year)
    }
    fun updataAllData(Type:String,Catagory:String,Account:String,Datee:String,Amount:Double,DateMOnth:String,Id:Int,Note:String,Year:String){
        return repostry.updataAllData(Type,Catagory,Account,Datee,Amount,DateMOnth,Id,Note,Year)
    }

}