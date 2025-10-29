package com.bodeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bodeapp.model.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {

    /**
     * Obtiene ventas por fecha específica
     * Formato fecha: "YYYY-MM-DD"
     */
    @Query("SELECT * FROM Venta WHERE date(timestamp) = :fecha ORDER BY timestamp DESC")
    fun ventasPorFecha(fecha: String): Flow<List<Venta>>

    /**
     * Obtiene ventas del día actual
     * CORREGIDO: Usa 'localtime' para zona horaria correcta
     */
    @Query("SELECT * FROM Venta WHERE date(timestamp) = date('now', 'localtime') ORDER BY timestamp DESC")
    fun ventasDelDia(): Flow<List<Venta>>

    /**
     * Inserta una nueva venta
     */
    @Insert
    suspend fun insertVenta(venta: Venta): Long

    /**
     * Transacción: insertar venta y descontar stock
     * Se ejecuta de forma atómica
     */
    @Transaction
    suspend fun insertVentaAndReduceStock(db: AppDatabase, venta: Venta, cantidad: Int) {
        // 1. Verificar que el producto existe y tiene stock suficiente
        val producto = db.productoDao().findById(venta.productoId)
            ?: throw IllegalStateException("Producto no encontrado")

        if (producto.stock < cantidad) {
            throw IllegalStateException("Stock insuficiente. Disponible: ${producto.stock}")
        }

        // 2. Insertar la venta
        insertVenta(venta)

        // 3. Reducir el stock
        val nuevoStock = producto.stock - cantidad
        db.productoDao().setStock(venta.productoId, nuevoStock)
    }

    /**
     * Calcula el total de ventas por fecha
     */
    @Query("SELECT SUM(total) FROM Venta WHERE date(timestamp) = :fecha")
    suspend fun totalPorFecha(fecha: String): Double?

    /**
     * Calcula el total de ventas del día actual
     */
    @Query("SELECT SUM(total) FROM Venta WHERE date(timestamp) = date('now', 'localtime')")
    suspend fun totalDelDia(): Double?
}

/**
 * CAMBIOS IMPORTANTES:
 *
 * ✅ Agregado 'localtime' en ventasDelDia() para usar zona horaria local
 * ✅ Mejorada validación de stock en insertVentaAndReduceStock
 * ✅ Agregado totalDelDia() para obtener total de ventas del día
 *
 * NOTA: Si aún no funciona, el problema puede estar en el formato del timestamp
 * al crear las ventas. Debe ser formato ISO: "YYYY-MM-DDTHH:MM:SS"
 */