package com.example.indicadoresapp.modelos.usuarios

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SIPGUsuarios")
data class UsuariosItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val clave: String,
    val completo: String,
    val correo: String,
    val idAgen: Int?,
    val depto: String?,
    val idRegion: Int?,
    val tipo: String?,
    val id_empleado: Int?,
    val token_movil: String?
)