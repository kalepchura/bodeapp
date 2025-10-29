package com.bodeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bodeapp.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    /**
     * Obtiene todos los productos (activos e inactivos)
     */
    @Query("SELECT * FROM Producto ORDER BY nombre ASC")
    fun getAll(): Flow<List<Producto>>

    /**
     * Obtiene solo productos activos (activo = 1)
     */
    @Query("SELECT * FROM Producto WHERE activo = 1 ORDER BY nombre ASC")
    fun getActivos(): Flow<List<Producto>>

    /**
     * Busca un producto por ID
     * Retorna null si no existe
     */
    @Query("SELECT * FROM Producto WHERE id = :id")
    suspend fun findById(id: Int): Producto?

    /**
     * Inserta un nuevo producto
     * Retorna el ID generado
     */
    @Insert
    suspend fun insert(producto: Producto): Long

    /**
     * Actualiza un producto existente
     */
    @Update
    suspend fun update(producto: Producto)

    /**
     * Elimina un producto por ID (hard delete)
     */
    @Query("DELETE FROM Producto WHERE id = :id")
    suspend fun deleteById(id: Int)

    /**
     * Marca un producto como inactivo (soft delete)
     */
    @Query("UPDATE Producto SET activo = 0 WHERE id = :id")
    suspend fun markInactive(id: Int)

    /**
     * Actualiza el stock de un producto
     * Usado por transacciones de ventas y compras
     */
    @Query("UPDATE Producto SET stock = :newStock WHERE id = :id")
    suspend fun setStock(id: Int, newStock: Int)

    /**
     * Incrementa el stock de un producto
     * Útil para compras
     */
    @Query("UPDATE Producto SET stock = stock + :cantidad WHERE id = :id")
    suspend fun incrementStock(id: Int, cantidad: Int)

    /**
     * Decrementa el stock de un producto
     * Útil para ventas (validar antes que stock >= cantidad)
     */
    @Query("UPDATE Producto SET stock = stock - :cantidad WHERE id = :id")
    suspend fun decrementStock(id: Int, cantidad: Int)
}

/**
 * MÉTODOS CLAVE:
 *
 * ✅ getActivos() - Retorna Flow para actualización automática en UI
 * ✅ findById() - Busca producto para validaciones
 * ✅ setStock() - Usado en transacciones de ventas/compras
 * ✅ markInactive() - Soft delete para mantener historial
 */