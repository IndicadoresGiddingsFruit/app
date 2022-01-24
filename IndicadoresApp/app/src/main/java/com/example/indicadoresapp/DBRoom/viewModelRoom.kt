package com.example.indicadoresapp.DBRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class viewModelRoom (application: Application): AndroidViewModel(application){
    private val dataBase=DataBase.getDatabase(application).DBRoom()
    private val repository: Repository

    private val getAllMuestreos:LiveData<List<MuestreosItem>>

    init{
        repository=Repository(dataBase)
        getAllMuestreos=repository.getAllMuestreos
    }

    fun insertMuestreo(prodMuestreo: ProdMuestreo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(prodMuestreo)
        }
    }
}