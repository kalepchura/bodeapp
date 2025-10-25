package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CierreScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Cierre de Caja / Reportes", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Text("Ventas del día: -")
        Text("Compras del día: -")
        Text("Utilidad: -")
        Spacer(Modifier.height(12.dp))
        Button(onClick = { /* Día 5 */ }) { Text("Generar cierre") }
    }
}