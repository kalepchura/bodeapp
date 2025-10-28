package com.bodeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bodeapp.data.local.entities.Compra
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {

    @Insert
    suspend fun insert(compra: Compra): Long

    @Query("SELECT * FROM Compra WHERE date(timestamp) = date(:fecha) ORDER BY timestamp DESC")
    fun getComprasPorFecha(fecha: String): Flow<List<Compra>>

    @Query("SELECT * FROM Compra ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Compra>>
}
