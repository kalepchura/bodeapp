package com.bodeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.bodeapp.ui.AppTopBar
import com.bodeapp.ui.Routes

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
            composable(Routes.PRODUCTOS) { CenterText("Productos") }
            composable(Routes.VENTAS)    { CenterText("Ventas") }
            composable(Routes.COMPRAS)   { CenterText("Compras") }
            composable(Routes.CIERRE)    { CenterText("Cierre / Reportes") }
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
