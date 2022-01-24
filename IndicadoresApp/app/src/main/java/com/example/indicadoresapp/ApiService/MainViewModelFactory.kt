package com.example.indicadoresapp.ApiService

import androidx.lifecycle.ViewModelProvider


class MainViewModelFactory ( private val repository:Repository): ViewModelProvider.Factory{

    /*override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModel(repository) as T
    }*/

    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(repository) as T
    }
}