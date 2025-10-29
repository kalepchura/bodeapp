package com.bodeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bodeapp.model.Producto

@Composable
fun ProductForm(initial: Producto?, onSave: (String, Double, Int) -> Unit, onCancel: () -> Unit) {
    var nombre by remember { mutableStateOf(initial?.nombre ?: "") }
    var precio by remember { mutableStateOf(initial?.precioUnitario?.toString() ?: "") }
    var stock by remember { mutableStateOf(initial?.stock?.toString() ?: "0") }


    Card(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio unitario") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock inicial") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onCancel) { Text("Cancelar") }
                TextButton(onClick = {
                    val p = nombre.trim()
                    val pr = precio.toDoubleOrNull() ?: -1.0
                    val s = stock.toIntOrNull() ?: Int.MIN_VALUE
// VALIDACIONES
                    when {
                        p.isBlank() -> { /* snackbar: Ingrese el nombre del producto. */ }
                        pr <= 0.0 -> { /* snackbar: Ingrese un precio mayor a 0. */ }
                        s < 0 -> { /* snackbar: Stock debe ser 0 o mayor. */ }
                        else -> onSave(p, pr, s)
                    }
                }) { Text("Guardar") }
            }
        }
    }
}