package com.bodeapp.data

import androidx.room.*
import com.bodeapp.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM Producto WHERE activo = 1 ORDER BY nombre")
    fun getActivos(): Flow<List<Producto>>


    @Query("SELECT * FROM Producto ORDER BY nombre")
    fun getAll(): Flow<List<Producto>>


    @Query("SELECT * FROM Producto WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Producto?


    @Insert
    suspend fun insert(producto: Producto): Long


    @Update
    suspend fun update(producto: Producto)


    @Query("DELETE FROM Producto WHERE id = :id")
    suspend fun deleteById(id: Int)


    @Query("UPDATE Producto SET activo = 0 WHERE id = :id")
    suspend fun markInactive(id: Int)


    @Query("UPDATE Producto SET stock = :stock WHERE id = :id")
    suspend fun setStock(id: Int, stock: Int)
}