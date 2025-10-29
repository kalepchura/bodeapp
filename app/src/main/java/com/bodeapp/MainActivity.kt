package com.bodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.bodeapp.data.AppDatabase
import com.bodeapp.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    // 🔹    Cambio: usamos lazy para inicializar la DB solo cuando se necesite, evitando bloquear la UI
    private val db by lazy { AppDatabase.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // 🔹 No se cambia nada más aquí, pero db ya está lazy, por lo que no bloquea UI
                AppNavHost(db = db)
            }
        }
    }
}