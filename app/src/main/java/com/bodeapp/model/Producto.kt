package com.bodeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Producto")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precioUnitario: Double,
    val stock: Int,
    val activo: Int = 1 // 1 = activo, 0 = inactivo
)