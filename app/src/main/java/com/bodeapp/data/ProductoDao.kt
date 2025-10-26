package com.bodeapp.data

import androidx.room.*

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    suspend fun getAll(): List<ProductoEntity>

    @Insert
    suspend fun insert(producto: ProductoEntity)

    @Delete
    suspend fun delete(producto: ProductoEntity)
}
