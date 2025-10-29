package com.bodeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bodeapp.data.AppDatabase
import com.bodeapp.ui.screen.*

@Composable
fun AppNavHost(db: AppDatabase) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            // 🔹 HomeScreen no necesita db, solo navegación
            HomeScreen(onNavigate = { navController.navigate(it) })
        }

        composable("productos") {

            // 🔹 Pasamos db solo a la pantalla que realmente la usa
            ProductsScreen(db = db, onNavigate = { navController.navigate(it) })
        }

        composable("ventas") {
            SalesScreen(db = db)
        }

        composable("compras") {
            PurchasesScreen(db = db)
        }

        composable("reportes") {
            ReportsScreen(db = db)
        }
    }
}
