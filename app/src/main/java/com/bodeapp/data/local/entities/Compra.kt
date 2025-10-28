package com.bodeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Compra")
data class Compra(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val cantidad: Int,
    val costoUnitario: Double,
    val total: Double,
    val timestamp: String
)


