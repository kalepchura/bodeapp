package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable fun HomeScreen() = Center("Home")
@Composable fun ProductosScreen() = Center("Productos")
@Composable fun VentasScreen() = Center("Ventas")
@Composable fun ComprasScreen() = Center("Compras")
@Composable fun CierreScreen() = Center("Cierre / Reportes")

@Composable
private fun Center(text: String) {
    Scaffold { p ->
        Box(
            Modifier.fillMaxSize().padding(p),
            contentAlignment = Alignment.Center
        ) {
            Text(text, style = MaterialTheme.typography.headlineMedium)
        }
    }
}