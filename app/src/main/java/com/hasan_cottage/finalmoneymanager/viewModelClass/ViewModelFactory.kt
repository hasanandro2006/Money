package com.hasan_cottage.finalmoneymanager.viewModelClass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository

class ViewModelFactory(val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(repository ) as T
    }
}