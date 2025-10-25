package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VentasScreen() {
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Registrar Venta", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(producto, { producto = it }, label = { Text("Producto") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(cantidad, { cantidad = it }, label = { Text("Cantidad") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { /* Día 4: subtotal */ }) { Text("Agregar") }
    }
}