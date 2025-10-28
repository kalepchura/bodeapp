package com.bodeapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    onGuardarCompra: (String, Double, Int) -> Unit = { _, _, _ -> } // Callback opcional para guardar
) {
    var producto by remember { mutableStateOf("") }
    var costo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Compra / Insumo") }
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
                "Nueva Compra",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = producto,
                onValueChange = { producto = it },
                label = { Text("Producto o Insumo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = costo,
                onValueChange = { costo = it },
                label = { Text("Costo Unitario (S/.)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (producto.isBlank() || costo.isBlank() || cantidad.isBlank()) {
                        mensaje = "Por favor, complete todos los campos."
                    } else {
                        val costoDouble = costo.toDoubleOrNull()
                        val cantidadInt = cantidad.toIntOrNull()
                        if (costoDouble == null || cantidadInt == null) {
                            mensaje = "Ingrese valores numéricos válidos."
                        } else {
                            mensaje = "Compra registrada correctamente."
                            onGuardarCompra(producto, costoDouble, cantidadInt)
                            producto = ""
                            costo = ""
                            cantidad = ""
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Filled.AddShoppingCart, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Guardar Compra")
            }

            Spacer(Modifier.height(16.dp))

            if (mensaje.isNotEmpty()) {
                Text(
                    mensaje,
                    color = if (mensaje.contains("correctamente")) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
