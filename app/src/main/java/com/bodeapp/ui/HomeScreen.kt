package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("BodeApp", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        Text("Accesos rápidos", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        // Solo texto por ahora; la TopBar maneja la navegación.
        Text("• Productos")
        Text("• Ventas")
        Text("• Compras")
        Text("• Cierre / Reportes")
    }
}
