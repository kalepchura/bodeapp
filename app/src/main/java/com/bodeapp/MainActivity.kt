package com.bodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bodeapp.data.local.DatabaseProvider
import com.bodeapp.ui.navigation.AppNav
import com.bodeapp.ui.theme.BodeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BodeAppTheme {
                AppNav()
            }
        }
    }
}



