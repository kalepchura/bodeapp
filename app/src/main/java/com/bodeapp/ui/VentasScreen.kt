package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bodeapp.model.Producto

data class Venta(
    val producto: String,
    val cantidad: Int,
    val subtotal: Double
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen() {
    var productos by remember {
        mutableStateOf(
            mutableListOf(
                Producto("Arroz", 4.0, 20),
                Producto("Azúcar", 3.5, 15),
                Producto("Aceite", 8.0, 10)
            )
        )
    }

    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var cantidad by remember { mutableStateOf("") }
    var ventas by remember { mutableStateOf(listOf<Venta>()) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Registrar Venta", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = productoSeleccionado?.nombre ?: "",
                onValueChange = {},
                label = { Text("Producto") },
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                productos.forEach { producto ->
                    DropdownMenuItem(
                        text = { Text("${producto.nombre} (Stock: ${producto.stock})") },
                        onClick = {
                            productoSeleccionado = producto
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                val qty = cantidad.toIntOrNull() ?: 0
                val prod = productoSeleccionado

                if (prod != null && qty > 0 && prod.stock >= qty) {
                    val subtotal = prod.precio * qty
                    ventas = ventas + Venta(prod.nombre, qty, subtotal)

                    // Actualiza stock localmente
                    productos = productos.map {
                        if (it.nombre == prod.nombre) it.copy(stock = it.stock - qty)
                        else it
                    }.toMutableList()

                    cantidad = ""
                    productoSeleccionado = null
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = productoSeleccionado != null && cantidad.isNotEmpty()
        ) {
            Text("Registrar venta")
        }

        Spacer(Modifier.height(24.dp))

        Text("Ventas registradas:", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(ventas) { venta ->
                Card(
                    Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${venta.producto} x${venta.cantidad}")
                        Text("S/ ${venta.subtotal}")
                    }
                }
            }
        }
    }
}
