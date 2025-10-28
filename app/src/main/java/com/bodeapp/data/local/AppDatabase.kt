package com.bodeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bodeapp.data.local.dao.CompraDao
import com.bodeapp.data.local.dao.ProductoDao
import com.bodeapp.data.local.dao.VentaDao
import com.bodeapp.data.local.entities.Compra
import com.bodeapp.data.local.entities.Producto
import com.bodeapp.data.local.entities.Venta

@Database(
    entities = [Producto::class, Venta::class, Compra::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun ventaDao(): VentaDao
    abstract fun compraDao(): CompraDao
}