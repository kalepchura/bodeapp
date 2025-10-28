package com.bodeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Venta")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: Double,
    val total: Double,
    val timestamp: String // ISO 8601: Instant.now().toString()
)
