package com.bodeapp.data.local.dao

import androidx.room.*
import com.bodeapp.data.local.entities.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM Producto WHERE activo = 1 ORDER BY nombre")
    fun getAllActivos(): Flow<List<Producto>>

    @Query("SELECT * FROM Producto ORDER BY nombre")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM Producto WHERE id = :id")
    suspend fun getById(id: Int): Producto?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(producto: Producto): Long

    @Update
    suspend fun update(producto: Producto)

    @Query("DELETE FROM Producto WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("UPDATE Producto SET activo = 0 WHERE id = :id")
    suspend fun marcarInactivo(id: Int)

    @Query("UPDATE Producto SET stock = stock - :cantidad WHERE id = :id")
    suspend fun reducirStock(id: Int, cantidad: Int)

    @Query("UPDATE Producto SET stock = stock + :cantidad WHERE id = :id")
    suspend fun aumentarStock(id: Int, cantidad: Int)
}
