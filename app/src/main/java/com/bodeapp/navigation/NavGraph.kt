package com.bodeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController

object Routes {
    const val HOME = "home"
    const val PRODUCTOS = "productos"
    const val VENTAS = "ventas"
    const val COMPRAS = "compras"
    const val CIERRE = "cierre"
}

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.HOME, modifier = modifier) {
        composable(Routes.HOME) { }
        composable(Routes.PRODUCTOS) { }
        composable(Routes.VENTAS) { }
        composable(Routes.COMPRAS) { }
        composable(Routes.CIERRE) { }
    }
}
