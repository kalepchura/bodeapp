package com.bodeapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Storefront

@Composable
fun HomeScreen(nav: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "BodeApp",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Control de ventas e inventario (demo UI)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        // Tarjetas de acciones (2 columnas)
        ActionGrid(
            items = listOf(
                ActionItem("Productos",  "Registrar y listar", Icons.Filled.Storefront) { nav?.navigate(Routes.PRODUCTOS) },
                ActionItem("Ventas",     "Registrar venta",    Icons.Filled.PointOfSale) { nav?.navigate(Routes.VENTAS) },
                ActionItem("Compras",    "Registrar compra",   Icons.Filled.ReceiptLong) { nav?.navigate(Routes.COMPRAS) },
                ActionItem("Cierre",     "Reporte del día",    Icons.Filled.Assessment)  { nav?.navigate(Routes.CIERRE) }
            )
        )
    }
}

private data class ActionItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

@Composable
private fun ActionGrid(items: List<ActionItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (row in items.chunked(2)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { item ->
                    ActionCard(item, modifier = Modifier.weight(1f))
                }
                if (row.size == 1) Spacer(Modifier.weight(1f)) // balancea si hay impar
            }
        }
    }
}

@Composable
private fun ActionCard(item: ActionItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .heightIn(min = 110.dp)
            .clickable { item.onClick() },
        elevation = elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(item.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(2.dp))
                Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
