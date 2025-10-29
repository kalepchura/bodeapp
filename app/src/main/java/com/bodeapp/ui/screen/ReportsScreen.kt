package com.bodeapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bodeapp.data.AppDatabase
import com.bodeapp.repository.BodegaRepository
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(db: AppDatabase) {

    // Repositorio
    val repo = remember { BodegaRepository(db) }

    // CoroutineScope para llamadas suspend
    val scope = rememberCoroutineScope()

    // Estado local
    var fecha by remember { mutableStateOf(java.time.LocalDate.now().toString()) }
    var totalVentas by remember { mutableStateOf(0.0) }
    var totalCompras by remember { mutableStateOf(0.0) }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Cierre / Reportes") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Campo para ingresar la fecha
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (YYYY-MM-DD)") }
            )

            // Botón para calcular totales
            Button(onClick = {
                scope.launch {
                    totalVentas = repo.totalVentasPorFecha(fecha)
                    totalCompras = repo.totalComprasPorFecha(fecha)
                }
            }) {
                Text("Calcular Totales")
            }

            // Mostrar resultados
            Text("Total ventas: S/ %.2f".format(totalVentas))
            Text("Total compras: S/ %.2f".format(totalCompras))
            Text("Utilidad: S/ %.2f".format(totalVentas - totalCompras))

            // Botón placeholder para CSV
            Button(onClick = {
                // Implementar exportación a CSV si deseas
            }) {
                Text("Generar reporte (CSV)")
            }
        }
    }
}


