package com.bodeapp.repository

import com.bodeapp.data.AppDatabase
import com.bodeapp.model.Compra
import com.bodeapp.model.Producto
import com.bodeapp.model.Venta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BodegaRepository(private val db: AppDatabase) {

    // ==================== FLOWS REACTIVOS ====================
    // Se actualizan automáticamente cuando hay cambios en la BD

    val productosActivos: Flow<List<Producto>> =
        db.productoDao().getActivos().flowOn(Dispatchers.IO)

    val productosAll: Flow<List<Producto>> =
        db.productoDao().getAll().flowOn(Dispatchers.IO)

    val ventasDelDia: Flow<List<Venta>> =
        db.ventaDao().ventasDelDia().flowOn(Dispatchers.IO)

    val comprasAll: Flow<List<Compra>> =
        db.compraDao().allCompras().flowOn(Dispatchers.IO)

    // ==================== PRODUCTOS ====================

    /**
     * Agrega un nuevo producto
     * @return ID del producto insertado
     */
    suspend fun addProducto(producto: Producto): Long {
        return db.productoDao().insert(producto)
    }

    /**
     * Actualiza un producto existente
     */
    suspend fun updateProducto(producto: Producto) {
        db.productoDao().update(producto)
    }

    /**
     * Elimina o marca como inactivo un producto
     * - Si el producto tiene historial (ventas/compras), se marca como inactivo
     * - Si no tiene historial, se elimina físicamente
     */
    suspend fun deleteOrMarkProducto(productoId: Int) {
        try {
            // Intentar eliminar (hard delete)
            db.productoDao().deleteById(productoId)
        } catch (e: Exception) {
            // Si falla por FK constraints, marcar como inactivo (soft delete)
            db.productoDao().markInactive(productoId)
        }
    }

    // ==================== VENTAS ====================

    /**
     * Registra una nueva venta
     * Pasos:
     * 1. Valida que el producto existe
     * 2. Valida cantidad > 0
     * 3. Valida stock suficiente
     * 4. Calcula precio y total
     * 5. Genera timestamp
     * 6. Inserta venta y reduce stock (transacción atómica)
     *
     * El Flow ventasDelDia se actualiza automáticamente
     */
    suspend fun registerVenta(productoId: Int, cantidad: Int) {
        // 1. Validar que el producto existe
        val producto = db.productoDao().findById(productoId)
            ?: throw IllegalStateException("Producto no encontrado")

        // 2. Validar cantidad
        if (cantidad <= 0) {
            throw IllegalArgumentException("Cantidad debe ser mayor a 0")
        }

        // 3. Validar stock suficiente
        if (cantidad > producto.stock) {
            throw IllegalStateException("Stock insuficiente. Disponible: ${producto.stock}")
        }

        // 4. Calcular precio y total
        val precio = producto.precioUnitario
        val total = String.format("%.2f", precio * cantidad).toDouble()

        // 5. Generar timestamp en formato ISO
        // Formato: "2025-10-28T14:30:00"
        val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        // 6. Crear objeto venta
        val venta = Venta(
            productoId = productoId,
            cantidad = cantidad,
            precioUnitario = precio,
            total = total,
            timestamp = now
        )

        // 7. Ejecutar transacción (insertar venta + reducir stock)
        // Si algo falla, se hace rollback automático
        db.ventaDao().insertVentaAndReduceStock(db, venta, cantidad)
    }

    // ==================== COMPRAS ====================

    /**
     * Registra una nueva compra
     * Pasos:
     * 1. Valida cantidad > 0
     * 2. Valida costo > 0
     * 3. Calcula total
     * 4. Genera timestamp
     * 5. Inserta compra y aumenta stock (transacción atómica)
     */
    suspend fun registerCompra(productoId: Int, cantidad: Int, costoUnitario: Double) {
        // 1. Validar cantidad
        if (cantidad <= 0) {
            throw IllegalArgumentException("Cantidad debe ser mayor a 0")
        }

        // 2. Validar costo
        if (costoUnitario <= 0.0) {
            throw IllegalArgumentException("Costo debe ser mayor a 0")
        }

        // 3. Calcular total
        val total = String.format("%.2f", costoUnitario * cantidad).toDouble()

        // 4. Generar timestamp
        val now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        // 5. Crear objeto compra
        val compra = Compra(
            productoId = productoId,
            cantidad = cantidad,
            costoUnitario = costoUnitario,
            total = total,
            timestamp = now
        )

        // 6. Ejecutar transacción (insertar compra + incrementar stock)
        db.compraDao().insertCompraAndIncreaseStock(db, compra, cantidad)
    }

    // ==================== REPORTES ====================

    /**
     * Obtiene el total de ventas por fecha específica
     * @param fechaISO Formato: "YYYY-MM-DD" (ej: "2025-10-28")
     * @return Total de ventas o 0.0 si no hay ventas
     */
    suspend fun totalVentasPorFecha(fechaISO: String): Double {
        return db.ventaDao().totalPorFecha(fechaISO) ?: 0.0
    }

    /**
     * Obtiene el total de compras por fecha específica
     * @param fechaISO Formato: "YYYY-MM-DD" (ej: "2025-10-28")
     * @return Total de compras o 0.0 si no hay compras
     */
    suspend fun totalComprasPorFecha(fechaISO: String): Double {
        return db.compraDao().totalPorFecha(fechaISO) ?: 0.0
    }
}

///**
// * ============================================================
// * NOTAS IMPORTANTES SOBRE EL FUNCIONAMIENTO:
// * ============================================================
// *
// * 1. FLOWS REACTIVOS:
// *    - Los Flow se actualizan automáticamente cuando hay cambios
// *    - No necesitas llamar manualmente a refrescar
// *    - Room observa cambios en las tablas y emite nuevos valores
// *
// * 2. TIMESTAMP:
// *    - Formato: ISO_LOCAL_DATE_TIME ("2025-10-28T14:30:00")
// *    - Compatible con SQLite date() function
// *    - Permite filtrar por fecha correctamente
// *
// * 3. TRANSACCIONES:
// *    - insertVentaAndReduceStock: atómica (todo o nada)
// *    - Si falla reducir stock, no se inserta la venta
// *    - Si falla insertar venta, no se reduce stock
// *
// * 4. VALIDACIONES:
// *    - Todas las validaciones lanzan excepciones descriptivas
// *    - Las excepciones son capturadas en el ViewModel
// *    - Se muestran al usuario como mensajes de error
// * ============================================================