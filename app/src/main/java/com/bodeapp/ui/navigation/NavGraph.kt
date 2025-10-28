package com.bodeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.bodeapp.ui.AppTopBar
import com.bodeapp.ui.CierreScreen
import com.bodeapp.ui.ComprasScreen
import com.bodeapp.ui.HomeScreen
import com.bodeapp.ui.ProductosScreen
import com.bodeapp.ui.Routes
import com.bodeapp.ui.VentasScreen

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    Scaffold(
        topBar = { AppTopBar(nav) }
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = Routes.HOME,
            modifier = modifier.then(Modifier.padding(inner))
        ) {
            composable(Routes.HOME) { HomeScreen() }


            // Placeholders (Kevin los reemplaza en su rama)
            composable(Routes.PRODUCTOS) { ProductosScreen() }
            composable(Routes.VENTAS) { VentasScreen() }
            composable(Routes.COMPRAS) { ComprasScreen() }
            composable(Routes.CIERRE) { CierreScreen() }
        }
    }
}

@Composable
private fun CenterText(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun HomeScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("BodeApp", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        Text("Accesos: Productos • Ventas • Compras • Cierre")
    }
}
