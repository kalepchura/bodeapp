package com.bodeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.repository.BodegaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VentasViewModel(private val repo: BodegaRepository) : ViewModel() {
    val productos = repo.productosActivos
    val ventasDelDia = repo.ventasDelDia


    fun registrarVenta(productoId: Int?, cantidad: Int?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (productoId == null || cantidad == null) throw IllegalArgumentException("Por favor complete todos los campos.")
                repo.registerVenta(productoId, cantidad)
                launch(Dispatchers.Main) { onSuccess() }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { onError(e.message ?: "Error al registrar venta. Intente de nuevo.") }
            }
        }
    }
}


class ComprasViewModel(private val repo: BodegaRepository) : ViewModel() {
    val productos = repo.productosActivos
    val compras = repo.comprasAll


    fun registrarCompra(productoId: Int?, cantidad: Int?, costo: Double?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (productoId == null || cantidad == null || costo == null) throw IllegalArgumentException("Por favor complete todos los campos.")
                repo.registerCompra(productoId, cantidad, costo)
                launch(Dispatchers.Main) { onSuccess() }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { onError(e.message ?: "Error al registrar compra. Intente de nuevo.") }
            }
        }
    }
}