package com.hasan_cottage.finalmoneymanager.viewmodelclass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel

class ViewmodelFactory( val repostry: Repostry): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Appviewmodel(repostry ) as T
    }
}