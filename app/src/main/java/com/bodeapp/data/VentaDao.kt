package com.bodeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bodeapp.model.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Query("SELECT * FROM Venta WHERE date(timestamp) = :fecha ORDER BY timestamp DESC")
    fun ventasPorFecha(fecha: String): Flow<List<Venta>>


    @Query("SELECT * FROM Venta WHERE date(timestamp) = date('now') ORDER BY timestamp DESC")
    fun ventasDelDia(): Flow<List<Venta>>


    @Insert
    suspend fun insertVenta(venta: Venta): Long


    // transaccional: insertar venta y descontar stock
    @Transaction
    suspend fun insertVentaAndReduceStock(db: AppDatabase, venta: Venta, cantidad: Int) {
// insert venta
        insertVenta(venta)
// reducir stock (usamos ProductoDao desde la db instance)
        val producto = db.productoDao().findById(venta.productoId)
            ?: throw IllegalStateException("Producto no encontrado")
        val nuevoStock = producto.stock - cantidad
        if (nuevoStock < 0) throw IllegalStateException("Stock insuficiente")
        db.productoDao().setStock(venta.productoId, nuevoStock)
    }


    @Query("SELECT SUM(total) FROM Venta WHERE date(timestamp) = :fecha")
    suspend fun totalPorFecha(fecha: String): Double?
}