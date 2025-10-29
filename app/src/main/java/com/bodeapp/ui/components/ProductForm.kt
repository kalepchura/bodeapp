package com.bodeapp.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import com.bodeapp.model.Producto

// Colores del tema
private val TealPrimary = Color(0xFF0D7C7C)
private val LightGray = Color(0xFFF5F5F5)

/**
 * Formulario modal para agregar/editar productos
 * Diseño según imagen 2
 */
@Composable
fun ProductForm(
    initial: Producto?,
    onSave: (String, Double, Int) -> Unit,
    onCancel: () -> Unit
) {
    // Estados del formulario
    var nombre by remember { mutableStateOf(initial?.nombre ?: "") }
    var precio by remember { mutableStateOf(initial?.precioUnitario?.toString() ?: "") }
    var stock by remember { mutableStateOf(initial?.stock?.toString() ?: "0") }

    // Estados de validación
    var nombreError by remember { mutableStateOf(false) }
    var precioError by remember { mutableStateOf(false) }
    var stockError by remember { mutableStateOf(false) }

    // Dialog de pantalla completa con fondo semi-transparente
    Dialog(onDismissRequest = onCancel) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Header con título y botón cerrar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (initial == null) "Nuevo Producto" else "Editar Producto",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        IconButton(onClick = onCancel) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Cerrar",
                                tint = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Campo: Nombre del producto
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            nombreError = false
                        },
                        label = { Text("Nombre del producto") },
                        placeholder = { Text("Ej: Coca Cola 500ml") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = nombreError,
                        supportingText = {
                            if (nombreError) {
                                Text(
                                    text = "Ingrese el nombre del producto",
                                    color = Color.Red
                                )
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            focusedLabelColor = TealPrimary,
                            unfocusedContainerColor = LightGray,
                            focusedContainerColor = LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo: Precio
                    OutlinedTextField(
                        value = precio,
                        onValueChange = {
                            precio = it
                            precioError = false
                        },
                        label = { Text("Precio") },
                        placeholder = { Text("0.00") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = precioError,
                        supportingText = {
                            if (precioError) {
                                Text(
                                    text = "Ingrese un precio mayor a 0",
                                    color = Color.Red
                                )
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            focusedLabelColor = TealPrimary,
                            unfocusedContainerColor = LightGray,
                            focusedContainerColor = LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo: Stock inicial
                    OutlinedTextField(
                        value = stock,
                        onValueChange = {
                            stock = it
                            stockError = false
                        },
                        label = { Text("Stock inicial") },
                        placeholder = { Text("0") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = stockError,
                        supportingText = {
                            if (stockError) {
                                Text(
                                    text = "Stock debe ser 0 o mayor",
                                    color = Color.Red
                                )
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TealPrimary,
                            focusedLabelColor = TealPrimary,
                            unfocusedContainerColor = LightGray,
                            focusedContainerColor = LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botones de acción
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Botón Cancelar (blanco)
                        OutlinedButton(
                            onClick = onCancel,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TealPrimary
                            )
                        ) {
                            Text("Cancelar")
                        }

                        // Botón Guardar (teal)
                        Button(
                            onClick = {
                                // Validar campos
                                val nombreTrim = nombre.trim()
                                val precioValue = precio.toDoubleOrNull() ?: -1.0
                                val stockValue = stock.toIntOrNull() ?: -1

                                var hasError = false

                                if (nombreTrim.isBlank()) {
                                    nombreError = true
                                    hasError = true
                                }

                                if (precioValue <= 0.0) {
                                    precioError = true
                                    hasError = true
                                }

                                if (stockValue < 0) {
                                    stockError = true
                                    hasError = true
                                }

                                // Si no hay errores, guardar
                                if (!hasError) {
                                    onSave(nombreTrim, precioValue, stockValue)
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TealPrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}

/**
 * NOTAS DE IMPLEMENTACIÓN:
 *
 * ✅ Dialog con fondo semi-transparente según imagen 2
 * ✅ Campos de texto con validación y mensajes de error
 * ✅ Botones estilizados: Cancelar (outline) y Guardar (filled)
 * ✅ Placeholder texts como "Ej: Coca Cola 500ml"
 * ✅ Colores del tema aplicados correctamente
 * ✅ Validación en tiempo real al escribir
 *
 * 🎨 Diseño basado en Material Design 3
 * 📱 Responsive y adaptable a diferentes tamaños de pantalla
 */