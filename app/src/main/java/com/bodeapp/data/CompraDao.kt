package com.bodeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bodeapp.model.Compra
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {
    @Query("SELECT * FROM Compra ORDER BY timestamp DESC")
    fun allCompras(): Flow<List<Compra>>


    @Insert
    suspend fun insertCompra(compra: Compra): Long


    // transaccional: insertar compra y aumentar stock
    @Transaction
    suspend fun insertCompraAndIncreaseStock(db: AppDatabase, compra: Compra, cantidad: Int) {
        insertCompra(compra)
        val producto = db.productoDao().findById(compra.productoId)
            ?: throw IllegalStateException("Producto no encontrado")
        val nuevoStock = producto.stock + cantidad
        db.productoDao().setStock(compra.productoId, nuevoStock)
    }


    @Query("SELECT SUM(total) FROM Compra WHERE date(timestamp) = :fecha")
    suspend fun totalPorFecha(fecha: String): Double?
}

