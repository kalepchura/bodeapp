package com.bodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bodeapp.ui.theme.BodeAppTheme
import com.bodeapp.navigation.AppNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodeAppTheme {
                AppNav()   //pantallas según la ruta
            }
        }
    }
}
