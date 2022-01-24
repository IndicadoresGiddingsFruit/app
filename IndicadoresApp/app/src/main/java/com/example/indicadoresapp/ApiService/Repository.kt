package com.example.indicadoresapp.ApiService

import com.example.indicadoresapp.modelos.catalogos.CamposItem
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreoSector
import com.example.indicadoresapp.modelos.usuarios.UsuariosItem
import retrofit2.Call
import retrofit2.Response

class Repository {
    fun getUsuario(username:String, password:String): Call<List<UsuariosItem>> {
        return RetroInstance.api.getUsuario(username, password)
    }

    fun patchUsuario(patch:UsuariosItem): Call<UsuariosItem> {
        return RetroInstance.api.patchUsuario(patch)
    }

    fun getMuestreos(idAgen:Int, tipo:String, depto:String): Call<List<MuestreosItem>> {
       return RetroInstance.api.getMuestreos(idAgen, tipo, depto)
    }

    fun getCampos(cod_Prod: String, cod_Campo: Int): Call<List<CamposItem>> {
        return RetroInstance.api.getCampos(cod_Prod, cod_Campo)
    }

    fun getSectores(idMuestreo: Int): Call<List<ProdMuestreoSector>> {
        return RetroInstance.api.getSectores(idMuestreo)
    }

    fun delSector(id: Int): Call<ProdMuestreoSector> {
        return RetroInstance.api.delSector(id)
    }

    fun postMuestreos(param: String, post: ProdMuestreo): Call<ProdMuestreo> {
        return RetroInstance.api.postMuestreos(param, post)
    }

    fun putMuestreosInocuidad(id:Int,idAgen:Int,sector:Int,put: ProdMuestreo): Call<ProdMuestreo> {
        return RetroInstance.api.putMuestreosInocuidad(id,idAgen,sector,put)
    }
}