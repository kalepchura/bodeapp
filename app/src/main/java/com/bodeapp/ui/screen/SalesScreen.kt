package com.bodeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.bodeapp.viewmodel.VentasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(db: AppDatabase, onNavigate: (String) -> Unit = {}) {
    // Inicializar ViewModel y Repository
    val repo = remember { BodegaRepository(db) }
    val vm = remember { VentasViewModel(repo) }

    // IMPORTANTE: collectAsState observa cambios automáticamente
    val productos by vm.productos.collectAsState(initial = emptyList())
    val ventas by vm.ventasDelDia.collectAsState(initial = emptyList())

    // Estados del formulario
    var selectedId by remember { mutableStateOf<Int?>(null) }
    var cantidadText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Colores
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

            // ========== TARJETA: NUEVA VENTA ==========
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Título
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = tealDark
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Nueva Venta",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de producto
                    Text("Seleccionar producto", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    DropdownMenuSelector(
                        productos = productos,
                        selectedId = selectedId,
                        onSelect = {
                            selectedId = it
                            errorMessage = null // Limpiar error al seleccionar
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de cantidad
                    Text("Cantidad", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = cantidadText,
                        onValueChange = {
                            cantidadText = it
                            errorMessage = null // Limpiar error al escribir
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = errorMessage != null
                    )

                    // Calcular subtotal
                    val selected = productos.find { it.id == selectedId }
                    val cantidad = cantidadText.toIntOrNull() ?: 0
                    val subtotal = selected?.precioUnitario?.times(cantidad) ?: 0.0

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Subtotal: S/ %.2f".format(subtotal))

                    // Mostrar mensaje de error si existe
                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage!!,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón registrar venta
                    Button(
                        onClick = {
                            // IMPORTANTE: Validar antes de enviar
                            if (selectedId == null) {
                                errorMessage = "Seleccione un producto"
                                return@Button
                            }
                            if (cantidad <= 0) {
                                errorMessage = "Ingrese una cantidad válida"
                                return@Button
                            }
                            if (selected != null && cantidad > selected.stock) {
                                errorMessage = "Stock insuficiente. Disponible: ${selected.stock}"
                                return@Button
                            }

                            // Registrar venta
                            vm.registrarVenta(
                                productoId = selectedId,
                                cantidad = cantidad,
                                onSuccess = {
                                    // LIMPIAR FORMULARIO después de éxito
                                    selectedId = null
                                    cantidadText = ""
                                    errorMessage = null

                                    // Las listas se actualizan automáticamente gracias a Flow
                                },
                                onError = { error ->
                                    errorMessage = error
                                }
                            )
                        },
                        enabled = selected != null && cantidad >= 1 && cantidad <= (selected.stock),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = tealDark)
                    ) {
                        Text("Registrar venta", color = Color.White)
                    }
                }
            }

            // ========== TARJETA: VENTAS DEL DÍA ==========
            // IMPORTANTE: Calcula el total de las ventas observadas
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
                    Text(
                        "Ventas del día",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "S/ %.2f".format(totalVentas),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "${ventas.size} transacciones",
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // ========== TARJETA: HISTORIAL DEL DÍA ==========
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Historial del día",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (ventas.isEmpty()) {
                        Text("No hay ventas registradas hoy.", color = Color.Gray)
                    } else {
                        // IMPORTANTE: LazyColumn con items() observa la lista
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.height(200.dp) // Altura fija para scroll
                        ) {
                            items(ventas) { venta ->
                                val producto = productos.find { it.id == venta.productoId }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            producto?.nombre ?: "Producto #${venta.productoId}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            "Cantidad: ${venta.cantidad}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            "S/ %.2f".format(venta.total),
                                            fontWeight = FontWeight.Bold
                                        )
                                        // Mostrar hora
                                        Text(
                                            formatTimestamp(venta.timestamp),
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

/**
 * Formatea el timestamp para mostrar solo la hora
 * Entrada: "2025-10-28T14:30:00"
 * Salida: "14:30"
 */
fun formatTimestamp(timestamp: String): String {
    return try {
        val parts = timestamp.split("T")
        if (parts.size == 2) {
            val timeParts = parts[1].split(":")
            "${timeParts[0]}:${timeParts[1]}"
        } else {
            timestamp
        }
    } catch (e: Exception) {
        timestamp
    }
}

/**
 * ============================================================
 * ¿POR QUÉ AHORA SÍ SE ACTUALIZA AUTOMÁTICAMENTE?
 * ============================================================
 *
 * 1. FLOW + collectAsState:
 *    - vm.ventasDelDia es un Flow<List<Venta>>
 *    - collectAsState() observa cambios en el Flow
 *    - Cuando Room inserta una venta, emite nuevo valor
 *    - La UI se recompone automáticamente
 *
 * 2. Limpieza del formulario:
 *    - Después de onSuccess, se limpia selectedId y cantidadText
 *    - Esto evita registros duplicados
 *
 * 3. Cálculos reactivos:
 *    - totalVentas = ventas.sumOf { it.total }
 *    - Se recalcula automáticamente cuando ventas cambia
 *
 * 4. LazyColumn con items():
 *    - items(ventas) observa la lista
 *    - Se actualiza cuando hay nuevos elementos
 *
 * REQUISITOS PARA QUE FUNCIONE:
 * ✅ VentaDao.ventasDelDia() debe retornar Flow<List<Venta>>
 * ✅ La consulta debe filtrar por fecha actual
 * ✅ BodegaRepository.registerVenta() debe usar transacción
 * ✅ No necesitas refrescar manualmente
 * ============================================================
 */