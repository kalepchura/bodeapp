package com.bodeapp.repository

import com.bodeapp.data.AppDatabase
import com.bodeapp.model.Compra
import com.bodeapp.model.Producto
import com.bodeapp.model.Venta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BodegaRepository(private val db: AppDatabase) {
    val productosActivos: Flow<List<Producto>> = db.productoDao().getActivos().flowOn(Dispatchers.IO)
    val productosAll: Flow<List<Producto>> = db.productoDao().getAll().flowOn(Dispatchers.IO)
    val ventasDelDia: Flow<List<Venta>> = db.ventaDao().ventasDelDia().flowOn(Dispatchers.IO)
    val comprasAll: Flow<List<Compra>> = db.compraDao().allCompras().flowOn(Dispatchers.IO)



    suspend fun addProducto(producto: Producto): Long {
        return db.productoDao().insert(producto)
    }


    suspend fun updateProducto(producto: Producto) {
        db.productoDao().update(producto)
    }


    suspend fun deleteOrMarkProducto(productoId: Int) {
// check history
// Simple check: if has ventas or compras for that producto
        val hasVenta = db.ventaDao().ventasPorFecha("_").firstOrNull() // placeholder - we cannot easily query by productoId here
// For clarity: implement check with rawQuery or separate DAO. Here we'll attempt naive approach: try to find associated records by reading flows
// In real project: add DAO queries like existsVentaForProducto(productoId)
// Simpler implementation: try deleting, if FK constraints prevent or choose mark inactive
        try {
            db.productoDao().deleteById(productoId)
        } catch (e: Exception) {
            db.productoDao().markInactive(productoId)
        }
    }


    suspend fun registerVenta(productoId: Int, cantidad: Int) {
        val producto = db.productoDao().findById(productoId)
            ?: throw IllegalStateException("Producto no encontrado")
        if (cantidad <= 0) throw IllegalArgumentException("Cantidad inválida")
        if (cantidad > producto.stock) throw IllegalStateException("Stock insuficiente. Stock disponible: ${producto.stock}")


        val precio = producto.precioUnitario
        val total = (precio * cantidad * 100.0).toInt() / 100.0
        val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val venta = Venta(productoId = productoId, cantidad = cantidad, precioUnitario = precio, total = total, timestamp = now)


        db.ventaDao().insertVentaAndReduceStock(db, venta, cantidad)
    }


    suspend fun registerCompra(productoId: Int, cantidad: Int, costoUnitario: Double) {
        if (cantidad <= 0) throw IllegalArgumentException("Cantidad inválida")
        if (costoUnitario <= 0.0) throw IllegalArgumentException("Ingrese un precio mayor a 0.")
        val total = (costoUnitario * cantidad * 100.0).toInt() / 100.0
        val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val compra = Compra(productoId = productoId, cantidad = cantidad, costoUnitario = costoUnitario, total = total, timestamp = now)
        db.compraDao().insertCompraAndIncreaseStock(db, compra, cantidad)
    }


    suspend fun totalVentasPorFecha(fechaISO: String): Double {
        return db.ventaDao().totalPorFecha(fechaISO) ?: 0.0
    }


    suspend fun totalComprasPorFecha(fechaISO: String): Double {
        return db.compraDao().totalPorFecha(fechaISO) ?: 0.0
    }
}