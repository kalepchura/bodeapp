package com.bodeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bodeapp.data.AppDatabase
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.DropdownMenuSelector
import com.bodeapp.viewmodel.VentasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(db: AppDatabase, onNavigate: (String) -> Unit) {
    val repo = remember { BodegaRepository(db) }
    val vm = remember { VentasViewModel(repo) }
    val productos by vm.productos.collectAsState(initial = emptyList())
    val ventas by vm.ventasDelDia.collectAsState(initial = emptyList())

    var selectedId by remember { mutableStateOf<Int?>(null) }
    var cantidadText by remember { mutableStateOf("") }

    val tealDark = Color(0xFF1E8449)
    val grayBackground = Color(0xFFF2F2F2)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ventas", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = tealDark)
            )
        },
        containerColor = grayBackground
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🧾 TARJETA NUEVA VENTA
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = tealDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Nueva Venta", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Seleccionar producto", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    DropdownMenuSelector(
                        productos = productos,
                        selectedId = selectedId,
                        onSelect = { selectedId = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Cantidad", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = cantidadText,
                        onValueChange = { cantidadText = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                    )

                    val selected = productos.find { it.id == selectedId }
                    val cantidad = cantidadText.toIntOrNull() ?: 0
                    val subtotal = selected?.precioUnitario?.times(cantidad) ?: 0.0

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Subtotal: S/ %.2f".format(subtotal))

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            vm.registrarVenta(
                                selectedId,
                                cantidad,
                                onSuccess = { /* mostrar snackbar */ },
                                onError = { /* mostrar error */ }
                            )
                        },
                        enabled = selected != null && cantidad >= 1 && cantidad <= (selected?.stock ?: 0),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = tealDark)
                    ) {
                        Text("Registrar venta", color = Color.White)
                    }
                }
            }

            // 💰 TARJETA RESUMEN DE VENTAS DEL DÍA
            val totalVentas = ventas.sumOf { it.total }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = tealDark)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ventas del día", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("S/ %.2f".format(totalVentas), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${ventas.size} transacciones", color = Color.White.copy(alpha = 0.9f))
                }
            }

            // 📋 TARJETA HISTORIAL DEL DÍA
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Historial del día", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (ventas.isEmpty()) {
                        Text("No hay ventas registradas.", color = Color.Gray)
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(ventas) { v ->
                                val producto = productos.find { it.id == v.productoId }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(producto?.nombre ?: "Producto desconocido", fontWeight = FontWeight.Medium)
                                        Text("Cantidad: ${v.cantidad}", fontSize = 12.sp, color = Color.Gray)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("S/ %.2f".format(v.total), fontWeight = FontWeight.Bold)
                                        Text(v.timestamp.substringAfter(" "), fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
