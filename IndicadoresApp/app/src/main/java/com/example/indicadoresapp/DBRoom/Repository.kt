package com.example.indicadoresapp.DBRoom

import androidx.lifecycle.LiveData
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo

class Repository(private val dBRoom:DBRoom) {
    val getAllMuestreos: LiveData<List<MuestreosItem>> = dBRoom.getMuestreos()

    suspend fun insertData(prodMuestreo: ProdMuestreo) {
        dBRoom.insertData(prodMuestreo)
    }

}