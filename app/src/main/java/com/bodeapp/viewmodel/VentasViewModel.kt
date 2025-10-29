package com.bodeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.repository.BodegaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VentasViewModel(private val repo: BodegaRepository) : ViewModel() {

    // Flows reactivos - se actualizan automáticamente
    val productos = repo.productosActivos
    val ventasDelDia = repo.ventasDelDia

    /**
     * Registra una nueva venta
     * - Valida datos
     * - Ejecuta en background (IO)
     * - Callbacks en Main thread
     * - El Flow ventasDelDia se actualiza automáticamente
     */
    fun registrarVenta(
        productoId: Int?,
        cantidad: Int?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Validar que los campos no sean null
                if (productoId == null || cantidad == null) {
                    throw IllegalArgumentException("Por favor complete todos los campos")
                }

                // Registrar venta en el repositorio
                // Esto automáticamente:
                // 1. Inserta la venta en BD
                // 2. Reduce el stock
                // 3. Emite nuevo valor en ventasDelDia Flow
                repo.registerVenta(productoId, cantidad)

                // Callback de éxito en Main thread
                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                // Callback de error en Main thread
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Error al registrar venta")
                }
            }
        }
    }
}

/**
 * ViewModel para Compras
 */
class ComprasViewModel(private val repo: BodegaRepository) : ViewModel() {

    val productos = repo.productosActivos
    val compras = repo.comprasAll

    fun registrarCompra(
        productoId: Int?,
        cantidad: Int?,
        costo: Double?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (productoId == null || cantidad == null || costo == null) {
                    throw IllegalArgumentException("Por favor complete todos los campos")
                }

                repo.registerCompra(productoId, cantidad, costo)

                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Error al registrar compra")
                }
            }
        }
    }
}

/**
 * CAMBIOS REALIZADOS:
 *
 * ✅ Usar withContext(Dispatchers.Main) en lugar de launch(Dispatchers.Main)
 *    - Más seguro y eficiente
 *    - Garantiza que el callback se ejecute en el hilo principal
 *
 * ✅ Mensajes de error más claros
 *
 * FLUJO COMPLETO:
 * 1. Usuario presiona "Registrar venta"
 * 2. SalesScreen llama vm.registrarVenta()
 * 3. ViewModel valida y llama repo.registerVenta()
 * 4. Repository ejecuta transacción en BD
 * 5. VentaDao inserta venta y reduce stock
 * 6. Room detecta cambio y emite nuevo valor en Flow
 * 7. ventasDelDia Flow notifica a SalesScreen
 * 8. SalesScreen se recompone con nuevos datos
 * 9. Se actualiza: total, transacciones, historial
 * 10. onSuccess() limpia el formulario
 */