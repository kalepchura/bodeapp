package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CierreScreen(
    totalVentas: Double = 0.0,
    totalCompras: Double = 0.0,
    onGenerarCierre: (() -> Unit)? = null
) {
    var utilidad by remember { mutableStateOf(totalVentas - totalCompras) }
    var mensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cierre de Caja / Reportes") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Resumen del Día",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Ventas del día:", fontWeight = FontWeight.Medium)
                    Text(
                        text = "S/ %.2f".format(totalVentas),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(8.dp))

                    Text("Compras del día:", fontWeight = FontWeight.Medium)
                    Text(
                        text = "S/ %.2f".format(totalCompras),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.height(8.dp))

                    Divider(Modifier.padding(vertical = 8.dp))

                    Text("Utilidad:", fontWeight = FontWeight.Bold)
                    Text(
                        text = "S/ %.2f".format(utilidad),
                        style = MaterialTheme.typography.titleLarge,
                        color = if (utilidad >= 0) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = {
                    utilidad = totalVentas - totalCompras
                    mensaje = "Cierre generado correctamente."
                    onGenerarCierre?.invoke()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Filled.Summarize, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Generar Cierre")
            }

            Spacer(Modifier.height(16.dp))

            if (mensaje.isNotEmpty()) {
                Text(
                    mensaje,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
