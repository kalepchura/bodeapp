package com.bodeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bodeapp.data.AppDatabase
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.DropdownMenuSelector
import com.bodeapp.viewmodel.ComprasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasesScreen(db: AppDatabase, onNavigate: (String) -> Unit) {
    val repo = remember { BodegaRepository(db) }
    val vm = remember { ComprasViewModel(repo) }

    val productos by vm.productos.collectAsState(initial = emptyList())
    val compras by vm.compras.collectAsState(initial = emptyList())

    var selectedId by remember { mutableStateOf<Int?>(null) }
    var costoText by remember { mutableStateOf("") }
    var cantidadText by remember { mutableStateOf("") }

    val tealDark = Color(0xFF006657)
    val grayBackground = Color(0xFFF2F2F2)
    val cream = Color(0xFFFFFDD0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compras", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("home") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
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

            // 🧾 SECCIÓN: REGISTRAR COMPRA
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = tealDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Registrar Compra",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Nombre del producto", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    DropdownMenuSelector(
                        productos = productos,
                        selectedId = selectedId,
                        onSelect = { selectedId = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Costo unitario", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = costoText,
                        onValueChange = { costoText = it },
                        placeholder = { Text("0.00") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Cantidad comprada", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = cantidadText,
                        onValueChange = { cantidadText = it },
                        placeholder = { Text("0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    val cantidad = cantidadText.toIntOrNull() ?: 0
                    val costo = costoText.toDoubleOrNull() ?: 0.0
                    val totalCompra = costo * cantidad

                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(cream, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total de compra:", color = Color.Black)
                            Text(
                                "S/ %.2f".format(totalCompra),
                                color = tealDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            vm.registrarCompra(
                                selectedId,
                                cantidad,
                                costo,
                                onSuccess = { /* mostrar snackbar */ },
                                onError = { /* error */ }
                            )
                        },
                        enabled = selectedId != null && cantidad >= 1 && costo > 0.0,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = tealDark)
                    ) {
                        Text("Registrar compra", color = Color.White)
                    }
                }
            }

            // 💰 TARJETA DE RESUMEN DEL DÍA
            val totalCompras = compras.sumOf { it.total }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = tealDark)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Compras del día", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "S/ %.2f".format(totalCompras),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${compras.size} transacciones", color = Color.White.copy(alpha = 0.9f))
                }
            }

            // 📋 HISTORIAL DE COMPRAS RECIENTES
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Historial reciente",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (compras.isEmpty()) {
                        Text("No hay compras registradas.", color = Color.Gray)
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(compras) { c ->
                                val producto = productos.find { it.id == c.productoId }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(producto?.nombre ?: "Producto desconocido", fontWeight = FontWeight.Medium)
                                        Text(
                                            "${c.cantidad} unidades x S/ %.2f".format(c.costoUnitario),
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            "S/ %.2f".format(c.total),
                                            color = tealDark,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            c.timestamp.substringBefore(" "),
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
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
