package com.bodeapp.ui.screen

import androidx.compose.runtime.Composable



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bodeapp.data.AppDatabase
import com.bodeapp.model.Producto
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.DropdownMenuSelector
import com.bodeapp.ui.components.ProductForm
import com.bodeapp.viewmodel.ComprasViewModel
import com.bodeapp.viewmodel.ProductosViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasesScreen(db: AppDatabase) {
    val repo = remember { BodegaRepository(db) }
    val vm = remember { ComprasViewModel(repo) }
    val productos by vm.productos.collectAsState(initial = emptyList())
    val compras by vm.compras.collectAsState(initial = emptyList())
    var selectedId by remember { mutableStateOf<Int?>(null) }
    var cantidadText by remember { mutableStateOf("") }
    var costoText by remember { mutableStateOf("") }


    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Compras") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            DropdownMenuSelector(productos = productos, selectedId = selectedId, onSelect = { selectedId = it })

            OutlinedTextField(
                value = costoText,
                onValueChange = { costoText = it },
                label = { Text("Costo unitario") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            OutlinedTextField(
                value = cantidadText,
                onValueChange = { cantidadText = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            val cantidad = cantidadText.toIntOrNull() ?: 0
            val costo = costoText.toDoubleOrNull() ?: 0.0
            Text("Total compra: S/ %.2f".format(costo * cantidad), modifier = Modifier.padding(8.dp))

            Button(
                onClick = { vm.registrarCompra(selectedId, cantidad, costo, onSuccess = { }, onError = { }) },
                enabled = selectedId != null && cantidad >= 1 && costo > 0.0,
                modifier = Modifier.padding(8.dp)
            ) { Text("Registrar compra") }

            Text("Compras recientes", modifier = Modifier.padding(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)  // <- importante, usa el espacio restante
                    .padding(8.dp)
            ) {
                items(compras) { c ->
                    Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(c.timestamp)
                                Text("ProductoId: ${c.productoId}")
                            }
                            Text("S/ %.2f".format(c.total))
                        }
                    }
                }
            }
        }
    }
}