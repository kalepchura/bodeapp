package com.bodeapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bodeapp.data.AppDatabase
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.DropdownMenuSelector
import com.bodeapp.viewmodel.VentasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(db: AppDatabase) {
    val repo = remember { BodegaRepository(db) }
    val vm = remember { VentasViewModel(repo) }
    val productos by vm.productos.collectAsState(initial = emptyList())
    val ventas by vm.ventasDelDia.collectAsState(initial = emptyList())
    var selectedId by remember { mutableStateOf<Int?>(null) }
    var cantidadText by remember { mutableStateOf("") }


    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Ventas") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
// Selector simple
            DropdownMenuSelector(
                productos = productos,
                selectedId = selectedId,
                onSelect = { selectedId = it })
            val selected = productos.find { it.id == selectedId }
            Text("Precio unitario: ${selected?.precioUnitario?.let { "S/ %.2f".format(it) } ?: "-"}")
            OutlinedTextField(value = cantidadText, onValueChange = { cantidadText = it }, label = { Text("Cantidad") }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
            )
            val cantidad = cantidadText.toIntOrNull() ?: 0
            val subtotal = selected?.precioUnitario?.times(cantidad) ?: 0.0
            Text("Subtotal: S/ %.2f".format(subtotal))
            Button(onClick = {
                vm.registrarVenta(selectedId, cantidad, onSuccess = { /* snackbar Venta registrada */ }, onError = { /* snackbar error */ })
            }, enabled = selected != null && cantidad >= 1 && selected != null && cantidad <= (selected?.stock ?: 0)) {
                Text("Registrar venta")
            }


            Text("Ventas del día")
            LazyColumn { items(ventas) { v ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { Text("${v.timestamp} - ProductoId: ${v.productoId}"); Text("Cant: ${v.cantidad}") }
                        Text("S/ %.2f".format(v.total))
                    }
                }
            } }
        }
    }
}