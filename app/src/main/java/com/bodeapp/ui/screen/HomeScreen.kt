package com.bodeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CenterAlignedTopAppBar(title = { Text("BodeApp") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Botones para navegar
            Button(
                onClick = { onNavigate("productos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("📦 Productos") }

            Button(
                onClick = { onNavigate("ventas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("🛒 Ventas") }

            Button(
                onClick = { onNavigate("compras") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("💰 Compras") }

            Button(
                onClick = { onNavigate("reportes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) { Text("📊 Cierre / Reportes") }
        }
    }
}
