package com.bodeapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

object Routes {
    const val HOME = "home"
    const val PRODUCTOS = "productos"
    const val VENTAS = "ventas"
    const val COMPRAS = "compras"
    const val CIERRE = "cierre"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(nav: NavController) {
    CenterAlignedTopAppBar(
        title = { Text("BodeApp") },
        actions = {
            Row(Modifier.padding(end = 8.dp)) {
                TextButton({ nav.navigate(Routes.HOME) }) { Text("Home") }
                TextButton({ nav.navigate(Routes.PRODUCTOS) }) { Text("Productos") }
                TextButton({ nav.navigate(Routes.VENTAS) }) { Text("Ventas") }
                TextButton({ nav.navigate(Routes.COMPRAS) }) { Text("Compras") }
                TextButton({ nav.navigate(Routes.CIERRE) }) { Text("Cierre") }
            }
        }
    )
}

