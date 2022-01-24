package com.example.indicadoresapp.ApiService

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.indicadoresapp.modelos.catalogos.CamposItem
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreoSector
import com.example.indicadoresapp.modelos.usuarios.UsuariosItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ViewModel (private val repository: Repository): ViewModel() {
    val usuarios:MutableLiveData<Call<List<UsuariosItem>>> = MutableLiveData()
    val usuario:MutableLiveData<Call<UsuariosItem>> = MutableLiveData()

    //muestreos
    val responsegetmuestreos:MutableLiveData<Call<List<MuestreosItem>>> = MutableLiveData()
    val responsecampos:MutableLiveData<Call<List<CamposItem>>> = MutableLiveData()
    val responsesectores:MutableLiveData<Call<List<ProdMuestreoSector>>> = MutableLiveData()
    val responsedelsector:MutableLiveData<Call<ProdMuestreoSector>> = MutableLiveData()
    val muestreos:MutableLiveData<Call<ProdMuestreo>> = MutableLiveData()

    fun getUsuario(username : String, password : String){
        viewModelScope.launch {
            val response= repository.getUsuario(username,password)
            usuarios.value=response
        }
    }

    fun patchUsuario(patch:UsuariosItem) {
        viewModelScope.launch {
            val response=repository.patchUsuario(patch)
            usuario.value=response
        }
    }

    //muestreos
    fun getMuestreos(idAgen : Int, tipo : String, depto : String){
        viewModelScope.launch {
           val response= repository.getMuestreos(idAgen, tipo, depto)
            responsegetmuestreos.value=response
        }
    }

    fun getCampos(cod_Prod: String, cod_Campo: Int) {
        viewModelScope.launch {
            val response= repository.getCampos(cod_Prod, cod_Campo)
            responsecampos.value=response
        }
    }

    fun getSectores(idMuestreo: Int) {
        viewModelScope.launch {
            val response= repository.getSectores(idMuestreo)
            responsesectores.value=response
        }
    }

    fun deleteSector(id: Int) {
        viewModelScope.launch {
            val response= repository.delSector(id)
            responsedelsector.value=response
        }
    }

    fun postMuestreo(param: String, post:ProdMuestreo) {
        viewModelScope.launch {
            val response=repository.postMuestreos(param, post)
            muestreos.value=response
        }
    }

    fun putMuestreoInocuidad(id:Int,idAgen:Int,sector:Int,put: ProdMuestreo) {
        viewModelScope.launch {
            val response=repository.putMuestreosInocuidad(id,idAgen,sector,put)
            muestreos.value=response
        }
    }
}