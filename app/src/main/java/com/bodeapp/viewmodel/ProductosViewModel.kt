package com.bodeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.model.Producto
import com.bodeapp.repository.BodegaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductosViewModel(private val repo: BodegaRepository) : ViewModel() {
    val productos = repo.productosActivos
    val allProductos = repo.productosAll


    fun addProducto(nombre: String, precio: Double, stock: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (nombre.isBlank()) throw IllegalArgumentException("Ingrese el nombre del producto.")
                if (precio <= 0.0) throw IllegalArgumentException("Ingrese un precio mayor a 0.")
                if (stock < 0) throw IllegalArgumentException("Stock debe ser 0 o mayor.")
                val p = Producto(nombre = nombre.trim(), precioUnitario = precio, stock = stock)
                repo.addProducto(p)
                launch(Dispatchers.Main) { onSuccess() }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { onError(e.message ?: "Error al guardar producto. Intente de nuevo.") }
            }
        }
    }


    fun updateProducto(prod: Producto, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (prod.nombre.isBlank()) throw IllegalArgumentException("Ingrese el nombre del producto.")
                if (prod.precioUnitario <= 0.0) throw IllegalArgumentException("Ingrese un precio mayor a 0.")
                if (prod.stock < 0) throw IllegalArgumentException("Stock debe ser 0 o mayor.")
                repo.updateProducto(prod)
                launch(Dispatchers.Main) { onSuccess() }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { onError(e.message ?: "Error al guardar producto. Intente de nuevo.") }
            }
        }
    }


    fun deleteProducto(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteOrMarkProducto(id)
                launch(Dispatchers.Main) { onSuccess() }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { onError(e.message ?: "Error al guardar producto. Intente de nuevo.") }
            }
        }
    }
}


