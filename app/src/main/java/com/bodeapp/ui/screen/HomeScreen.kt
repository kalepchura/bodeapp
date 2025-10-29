package com.bodeapp.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores del tema
private val TealPrimary = Color(0xFF0D7C7C)
private val TealDark = Color(0xFF0A5F5F)
private val YellowCard = Color(0xFFFFC947)

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TealPrimary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "BodeApp",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Control de Inventario y Ventas",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Grid de tarjetas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Primera fila
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Productos (Teal)
                    MenuCard(
                        modifier = Modifier.weight(1f),
                        title = "Productos",
                        subtitle = "Gestionar\ninventario",
                        icon = "📦",
                        backgroundColor = TealDark,
                        onClick = { onNavigate("productos") }
                    )

                    // Ventas (Amarillo)
                    MenuCard(
                        modifier = Modifier.weight(1f),
                        title = "Ventas",
                        subtitle = "Registrar ventas",
                        icon = "🛒",
                        backgroundColor = YellowCard,
                        textColor = TealPrimary,
                        onClick = { onNavigate("ventas") }
                    )
                }

                // Segunda fila
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Compras (Teal)
                    MenuCard(
                        modifier = Modifier.weight(1f),
                        title = "Compras",
                        subtitle = "Registrar compras",
                        icon = "💰",
                        backgroundColor = TealDark,
                        onClick = { onNavigate("compras") }
                    )

                    // Reportes (Amarillo)
                    MenuCard(
                        modifier = Modifier.weight(1f),
                        title = "Reportes",
                        subtitle = "Cierre y reportes",
                        icon = "📊",
                        backgroundColor = YellowCard,
                        textColor = TealPrimary,
                        onClick = { onNavigate("reportes") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Text(
                text = "Versión 1.0.0",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: String,
    backgroundColor: Color,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    // Animación de escala cuando el cursor está sobre la tarjeta
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = tween(durationMillis = 200)
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxHeight()
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isHovered) 12.dp else 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icono circular
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = textColor.copy(alpha = 0.2f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = icon,
                        fontSize = 28.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Título
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtítulo
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.8f),
                lineHeight = 14.sp
            )
        }
    }
}