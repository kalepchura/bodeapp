package com.bodeapp.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bodeapp.data.local.entities.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {

    @Insert
    suspend fun insert(venta: Venta): Long

    @Query("SELECT * FROM Venta WHERE date(timestamp) = date(:fecha) ORDER BY timestamp DESC")
    fun getVentasPorFecha(fecha: String): Flow<List<Venta>>

    @Query("SELECT * FROM Venta ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Venta>>
}

