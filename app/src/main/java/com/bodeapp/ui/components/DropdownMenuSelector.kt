package com.bodeapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bodeapp.model.Producto

@Composable
fun DropdownMenuSelector(productos: List<Producto>, selectedId: Int?, onSelect: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val selectedName = productos.find { it.id == selectedId }?.nombre ?: "Seleccione producto"
    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) { Text(selectedName) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            productos.forEach { p ->
                DropdownMenuItem(text = { Text("${p.nombre} (Stock: ${p.stock})") }, onClick = { onSelect(p.id); expanded = false })
            }
        }
    }
}