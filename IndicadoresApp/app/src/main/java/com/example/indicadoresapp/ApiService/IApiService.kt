package com.example.indicadoresapp.ApiService

import com.example.indicadoresapp.modelos.catalogos.CamposItem
import com.example.indicadoresapp.modelos.muestreos.*
import com.example.indicadoresapp.modelos.usuarios.UsuariosItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IApiService {
    //Iniciar sesión
    @GET("usuarios/{username}/{password}")
    fun getUsuario(@Path("username") username: String, @Path("password") password: String) : Call<List<UsuariosItem>>

    //Guardar token móvil
    @PATCH("usuarios/")
    fun patchUsuario(@Body patch: UsuariosItem): Call<UsuariosItem>

    //cargar muestreos por idAgen
    @GET("muestreo/{idAgen}/{tipo}/{depto}")
    fun getMuestreos(@Path("idAgen") idAgen: Int, @Path("tipo") tipo: String, @Path("depto") depto: String) : Call<List<MuestreosItem>>

    //cargar campos por cod_prod
    @GET("campos/{cod_Prod}/{cod_Campo}")
    fun getCampos(@Path("cod_Prod") Cod_Prod: String, @Path("cod_Campo") Cod_Campo: Int): Call<List<CamposItem>>

    //cargar sectores por idMuestreo
    @GET("muestreosector/{idMuestreo}")
    fun getSectores(@Path("idMuestreo") idMuestreo: Int): Call<List<ProdMuestreoSector>>

    //eliminar sector
    @DELETE("muestreosector/{id}")
    fun delSector(@Path("id") id: Int): Call<ProdMuestreoSector>

    //crear nuevo muestreo
    @POST("muestreo/{param}")
    fun postMuestreos(@Path("param") param: String, @Body post:ProdMuestreo): Call<ProdMuestreo>

    //guardar fecha_ejecucion
    @PUT("muestreo/{id}/{idAgen}/{sector}")
    fun putMuestreosInocuidad(@Path("id") id: Int, @Path("idAgen") idAgen: Int, @Path("sector") sector: Int, @Body put:ProdMuestreo) : Call<ProdMuestreo>

    //liberar muestreo por producción
    @PATCH("muestreo/{id}")
    fun patchMuestreos(@Path("id") id: Int, @Body patch:ProdMuestreo): Call<ProdMuestreo>
}