package com.bodeapp.ui.screen

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
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bodeapp.data.AppDatabase
import com.bodeapp.model.Producto
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.ProductForm
import com.bodeapp.viewmodel.ProductosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(db: AppDatabase, onNavigate: (String) -> Unit) {
    val repo = remember { BodegaRepository(db) }
    val vm = remember { ProductosViewModel(repo) }
    val productos by vm.productos.collectAsState(initial = emptyList())
    val context = LocalContext.current
    var search by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(false) }
    var editProducto by remember { mutableStateOf<Producto?>(null) }


    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Productos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { editProducto = null; showForm = true }) { Text("+") }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Buscar") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            val filtered = if (search.isBlank()) productos else productos.filter { it.nombre.contains(search, ignoreCase = true) }

            if (filtered.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no hay productos — Agregar producto")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // <- IMPORTANTE
                        .padding(8.dp)
                ) {
                    items(filtered) { p ->
                        Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.clickable { /* ver detalle */ }) {
                                    Text(p.nombre)
                                    Text("S/ %.2f".format(p.precioUnitario))
                                    Text("Stock: ${p.stock}")
                                }
                                Row {
                                    TextButton(onClick = { editProducto = p; showForm = true }) { Text("Editar") }
                                    TextButton(onClick = {
                                        vm.deleteProducto(
                                            p.id,
                                            onSuccess = { /* snackbar */ },
                                            onError = { /* snackbar */ }
                                        )
                                    }) { Text("Eliminar") }
                                }
                            }
                        }
                    }
                }
            }

            if (showForm) {
                ProductForm(
                    initial = editProducto,
                    onSave = { nombre, precio, stock ->
                        if (editProducto == null) {
                            vm.addProducto(
                                nombre,
                                precio,
                                stock,
                                onSuccess = { showForm = false },
                                onError = { /* snackbar */ })
                        } else {
                            val updated = editProducto!!.copy(
                                nombre = nombre,
                                precioUnitario = precio,
                                stock = stock
                            )
                            vm.updateProducto(
                                updated,
                                onSuccess = { showForm = false },
                                onError = { /* snackbar */ })
                        }
                    },
                    onCancel = { showForm = false }
                )
            }
        }
    }
}