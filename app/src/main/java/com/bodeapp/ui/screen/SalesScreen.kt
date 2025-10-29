package com.bodeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.bodeapp.model.Producto
import com.bodeapp.model.Venta
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.DropdownMenuSelector
import com.bodeapp.viewmodel.VentasViewModel

// Colores del tema según la imagen
private val TealPrimary = Color(0xFF0D7C7C)
private val TealDark = Color(0xFF0A5F5F)
private val LightGray = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesScreen(db: AppDatabase, onNavigate: (String) -> Unit = {}) {
    // Inicializar ViewModel y Repository
    val repo = remember { BodegaRepository(db) }
    val vm = remember { VentasViewModel(repo) }
    val productos by vm.productos.collectAsState(initial = emptyList())
    val ventas by vm.ventasDelDia.collectAsState(initial = emptyList())

    // Estados del formulario
    var selectedId by remember { mutableStateOf<Int?>(null) }
    var cantidadText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ventas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("home") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TealPrimary
                )
            )

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightGray)
        ) {
            // ========== CARD: NUEVA VENTA ==========
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Título con icono
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Nueva venta",
                            tint = TealPrimary
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Nueva Venta",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TealPrimary
                        )
                    }

                    // Label: Seleccionar producto
                    Text(
                        text = "Seleccionar producto",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Dropdown selector de productos
                    DropdownMenuSelector(
                        productos = productos,
                        selectedId = selectedId,
                        onSelect = {
                            selectedId = it
                            errorMessage = null
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Label: Cantidad
                    Text(
                        text = "Cantidad",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Campo de cantidad
                    OutlinedTextField(
                        value = cantidadText,
                        onValueChange = {
                            cantidadText = it
                            errorMessage = null
                        },
                        placeholder = { Text("0") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            focusedLabelColor = TealPrimary,
                            unfocusedContainerColor = LightGray,
                            focusedContainerColor = LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar mensaje de error si existe
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Botón: Registrar venta
                    val selected = productos.find { it.id == selectedId }
                    val cantidad = cantidadText.toIntOrNull() ?: 0
                    val isValid = selected != null && cantidad >= 1 && cantidad <= selected.stock

                    Button(
                        onClick = {
                            if (selectedId != null && cantidad > 0) {
                                vm.registrarVenta(
                                    productoId = selectedId,
                                    cantidad = cantidad,
                                    onSuccess = {
                                        // Limpiar formulario después de éxito
                                        selectedId = null
                                        cantidadText = ""
                                        errorMessage = null
                                    },
                                    onError = { error ->
                                        // Mostrar mensaje de error
                                        errorMessage = error
                                    }
                                )
                            }
                        },
                        enabled = isValid,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TealPrimary,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text("Registrar venta", modifier = Modifier.padding(8.dp))
                    }
                }
            }

            // ========== CARD: VENTAS DEL DÍA ==========
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TealDark
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Ventas del día",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Total de ventas del día
                    val totalVentas = ventas.sumOf { it.total }
                    Text(
                        text = "S/%.2f".format(totalVentas),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Número de transacciones
                    Text(
                        text = "${ventas.size} transacciones",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ========== TÍTULO: HISTORIAL DEL DÍA ==========
            Text(
                text = "Historial del día",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // ========== LISTA DE VENTAS ==========
            if (ventas.isEmpty()) {
                // Mensaje cuando no hay ventas
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay ventas registradas hoy",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                // Lista de ventas
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ventas) { venta ->
                        VentaCard(venta = venta, productos = productos)
                    }

                    // Espacio extra al final
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

/**
 * Card individual de venta en el historial
 * Muestra: nombre producto, cantidad × precio, hora, total
 */
@Composable
fun VentaCard(
    venta: Venta,
    productos: List<Producto>
) {
    val producto = productos.find { it.id == venta.productoId }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información de la venta (izquierda)
            Column(modifier = Modifier.weight(1f)) {
                // Nombre del producto
                Text(
                    text = producto?.nombre ?: "Producto #${venta.productoId}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Cantidad × Precio unitario
                Row {
                    Text(
                        text = "Cant: ${venta.cantidad}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = " × ",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "S/%.2f".format(venta.precioUnitario),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Hora de la venta
                Text(
                    text = formatTimestamp(venta.timestamp),
                    fontSize = 11.sp,
                    color = Color.Gray.copy(alpha = 0.7f)
                )
            }

            // Total (derecha)
            Text(
                text = "S/%.2f".format(venta.total),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TealPrimary
            )
        }
    }
}

/**
 * Formatea el timestamp para mostrar solo hora
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
 * CARACTERÍSTICAS IMPLEMENTADAS:
 * ============================================================
 *
 * ✅ Diseño según imagen proporcionada
 * ✅ Card "Nueva Venta" con selector y campo de cantidad
 * ✅ Card "Ventas del día" con total y transacciones
 * ✅ Historial del día con lista de ventas
 * ✅ Actualización automática al registrar venta
 * ✅ Validación de stock antes de registrar
 * ✅ Mensajes de error descriptivos
 * ✅ Limpieza automática del formulario
 * ✅ Formato de hora en el historial (HH:MM)
 *
 * ============================================================
 */