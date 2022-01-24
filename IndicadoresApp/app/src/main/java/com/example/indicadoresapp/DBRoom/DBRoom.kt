package com.example.indicadoresapp.DBRoom
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.indicadoresapp.modelos.muestreos.MuestreosItem
import com.example.indicadoresapp.modelos.muestreos.ProdMuestreo

@Dao
interface DBRoom {
    @Query("Select * from ProdMuestreo order by fecha_solicitud desc")
    fun getMuestreos() : LiveData<List<MuestreosItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(prodMuestreo: ProdMuestreo)
}