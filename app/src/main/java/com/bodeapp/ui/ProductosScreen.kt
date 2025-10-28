package com.bodeapp.ui

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bodeapp.data.local.AppDatabase
import com.bodeapp.data.ProductoEntity
import kotlinx.coroutines.launch

@Composable
fun ProductosScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.productoDao()
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var productos by remember { mutableStateOf(listOf<ProductoEntity>()) }

    LaunchedEffect(Unit) {
        productos = dao.getAll()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Registrar Producto", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it },
            label = { Text("Stock inicial") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && precio.isNotBlank() && stock.isNotBlank()) {
                    val nuevo = ProductoEntity(
                        nombre = nombre,
                        precio = precio.toDouble(),
                        stock = stock.toInt()
                    )
                    scope.launch {
                        dao.insert(nuevo)
                        productos = dao.getAll()
                    }
                    nombre = ""
                    precio = ""
                    stock = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Guardar")
        }

        Spacer(Modifier.height(24.dp))

        Text("Productos registrados", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(productos) { p ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(p.nombre, style = MaterialTheme.typography.bodyLarge)
                        Text("Precio: S/. ${p.precio}")
                        Text("Stock: ${p.stock}")
                    }
                }
            }
        }
    }
}