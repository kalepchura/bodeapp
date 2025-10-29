package com.bodeapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bodeapp.model.Producto

// Colores del tema
private val TealPrimary = Color(0xFF0D7C7C)
private val LightGray = Color(0xFFF5F5F5)

/**
 * Componente Dropdown para seleccionar productos
 * Diseño según la imagen de SalesScreen
 *
 * @param productos Lista de productos disponibles
 * @param selectedId ID del producto seleccionado (null si no hay selección)
 * @param onSelect Callback cuando se selecciona un producto
 * @param modifier Modificador opcional
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuSelector(
    productos: List<Producto>,
    selectedId: Int?,
    onSelect: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado para controlar si el dropdown está expandido
    var expanded by remember { mutableStateOf(false) }

    // Buscar el producto seleccionado
    val selectedProducto = productos.find { it.id == selectedId }

    // Texto a mostrar en el campo
    val displayText = selectedProducto?.nombre ?: "-- Selecciona un producto --"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // Campo de texto (no editable, solo muestra la selección)
        OutlinedTextField(
            value = displayText,
            onValueChange = {}, // No editable
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Abrir selector",
                    tint = TealPrimary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // Importante para que funcione el dropdown
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TealPrimary,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = TealPrimary,
                unfocusedContainerColor = LightGray,
                focusedContainerColor = LightGray,
                disabledContainerColor = LightGray
            )
        )

        // Menú dropdown con las opciones
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Opción por defecto (deseleccionar)
            DropdownMenuItem(
                text = { Text("-- Selecciona un producto --", color = Color.Gray) },
                onClick = {
                    onSelect(null)
                    expanded = false
                }
            )

            // Separador visual
            if (productos.isNotEmpty()) {
                androidx.compose.material3.HorizontalDivider()
            }

            // Lista de productos disponibles
            productos.forEach { producto ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "${producto.nombre} (Stock: ${producto.stock})",
                            color = if (producto.stock > 0) Color.Black else Color.Red
                        )
                    },
                    onClick = {
                        onSelect(producto.id)
                        expanded = false
                    },
                    // Deshabilitar productos sin stock
                    enabled = producto.stock > 0
                )
            }

            // Mensaje si no hay productos disponibles
            if (productos.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No hay productos disponibles", color = Color.Gray) },
                    onClick = { },
                    enabled = false
                )
            }
        }
    }
}

/**
 * CARACTERÍSTICAS:
 * ✅ Muestra nombre del producto y stock disponible
 * ✅ Productos sin stock aparecen en rojo y deshabilitados
 * ✅ Placeholder: "-- Selecciona un producto --"
 * ✅ Opción para deseleccionar (volver a null)
 * ✅ Colores del tema aplicados
 * ✅ Responsive y adaptable
 */