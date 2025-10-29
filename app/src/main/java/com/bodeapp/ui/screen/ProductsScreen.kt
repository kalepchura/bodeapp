package com.bodeapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bodeapp.data.AppDatabase
import com.bodeapp.model.Producto
import com.bodeapp.repository.BodegaRepository
import com.bodeapp.ui.components.ProductForm
import com.bodeapp.viewmodel.ProductosViewModel

// Colores del tema según la imagen
private val TealPrimary = Color(0xFF0D7C7C)
private val GreenAvailable = Color(0xFF4CAF50)
private val LightGray = Color(0xFFF5F5F5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(db: AppDatabase, onNavigate: (String) -> Unit) {
    // Inicializar ViewModel y Repository
    val repo = remember { BodegaRepository(db) }
    val vm = remember { ProductosViewModel(repo) }
    val productos by vm.productos.collectAsState(initial = emptyList())

    // Estados locales
    var search by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(false) }
    var editProducto by remember { mutableStateOf<Producto?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                // TopBar personalizado según la imagen 1
                TopAppBar(
                    title = {
                        Text(
                            "Productos",
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
            },
            floatingActionButton = {
                // FAB amarillo con icono "+" según imagen 1
                FloatingActionButton(
                    onClick = {
                        editProducto = null
                        showForm = true
                    },
                    containerColor = Color(0xFFFFC947),
                    contentColor = TealPrimary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Agregar producto"
                    )

                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(LightGray)
            ) {
                // Campo de búsqueda
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Buscar producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TealPrimary,
                        focusedLabelColor = TealPrimary,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )

                // Filtrar productos según búsqueda
                val filtered = if (search.isBlank()) {
                    productos
                } else {
                    productos.filter {
                        it.nombre.contains(search, ignoreCase = true)
                    }
                }

                // Mostrar lista o mensaje vacío
                if (filtered.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Aún no hay productos\nToca el botón + para agregar",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                } else {
                    // Lista de productos según imagen 1
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filtered) { producto ->
                            ProductCard(
                                producto = producto,
                                onEdit = {
                                    editProducto = producto
                                    showForm = true
                                },
                                onDelete = {
                                    vm.deleteProducto(
                                        producto.id,
                                        onSuccess = { /* TODO: Mostrar mensaje de éxito */ },
                                        onError = { /* TODO: Mostrar mensaje de error */ }
                                    )
                                }
                            )
                        }

                        // Espacio extra al final para que el FAB no tape el último item
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }

        // Formulario modal (se muestra encima de todo) - Ver imagen 2
        if (showForm) {
            ProductForm(
                initial = editProducto,
                onSave = { nombre, precio, stock ->
                    if (editProducto == null) {
                        // Crear nuevo producto
                        vm.addProducto(
                            nombre = nombre,
                            precio = precio,
                            stock = stock,
                            onSuccess = { showForm = false },
                            onError = { /* TODO: Mostrar snackbar con error */ }
                        )
                    } else {
                        // Actualizar producto existente
                        val updated = editProducto!!.copy(
                            nombre = nombre,
                            precioUnitario = precio,
                            stock = stock
                        )
                        vm.updateProducto(
                            prod = updated,
                            onSuccess = { showForm = false },
                            onError = { /* TODO: Mostrar snackbar con error */ }
                        )
                    }
                },
                onCancel = { showForm = false }
            )
        }
    }
}

/**
 * Card individual de producto según el diseño de la imagen 1
 * Muestra: Nombre, Precio, Stock y badge "Disponible"
 */
@Composable
fun ProductCard(
    producto: Producto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }, // Al tocar la tarjeta, editar
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información del producto (izquierda)
            Column(modifier = Modifier.weight(1f)) {
                // Nombre del producto
                Text(
                    text = producto.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Precio y Stock en una fila
                Row {
                    Text(
                        text = "Precio: S/%.2f".format(producto.precioUnitario),
                        fontSize = 14.sp,
                        color = TealPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Stock: ${producto.stock}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // Badge "Disponible" (derecha) según imagen 1
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = GreenAvailable.copy(alpha = 0.15f)
            ) {
                Text(
                    text = if (producto.stock > 0) "Disponible" else "Agotado",
                    color = if (producto.stock > 0) GreenAvailable else Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

/**
 * NOTAS IMPORTANTES SOBRE LA IMPLEMENTACIÓN:
 *
 * ✅ Este archivo está corregido y debería funcionar correctamente
 *
 * 📋 SIGUIENTE PASO: Actualizar ProductForm.kt para que coincida con la imagen 2
 *
 * El ProductForm actual es muy básico. Necesita:
 * - Dialog/BottomSheet con fondo semi-transparente
 * - Diseño según imagen 2: "Nuevo Producto"
 * - Campos: Nombre del producto, Precio, Stock inicial
 * - Botones: Cancelar (blanco) y Guardar (teal)
 * - Validaciones mejoradas con mensajes de error
 */