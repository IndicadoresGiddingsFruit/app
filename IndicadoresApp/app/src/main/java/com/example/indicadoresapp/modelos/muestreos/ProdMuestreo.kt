package com.example.indicadoresapp.modelos.muestreos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProdMuestreo")
data class ProdMuestreo (
    @PrimaryKey(autoGenerate = true)
    val Id:Int?,
    val Cod_Empresa:Int?,
    val Cod_Prod:String?,
    val Cod_Campo:Int?,
    val Fecha_solicitud:String?,
    val Telefono:String?,
    val Inicio_cosecha:String?,
    val IdAgen:Int?,
    val Liberacion:String?,
    val Tarjeta:String?,
    val Fecha_ejecucion:String?,
    val IdSector:String?,
    val IdAgenI:String?,
    val IdAgen_Tarjeta:String?,
    val Liberar_Tarjeta:String?,
    val Temporada:String?,
    val ImageAutoriza:String?,
    val CajasEstimadas:Int?
)