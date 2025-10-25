package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductosScreen() {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Registrar Producto", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(precio, { precio = it }, label = { Text("Precio") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(stock, { stock = it }, label = { Text("Stock inicial") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { /* Día 4: guardar en Room */ }) { Text("Guardar") }
    }
}