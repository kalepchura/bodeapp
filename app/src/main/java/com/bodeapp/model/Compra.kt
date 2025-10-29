package com.bodeapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Compra",
    foreignKeys = [ForeignKey(
        entity = Producto::class,
        parentColumns = ["id"], childColumns = ["productoId"], onDelete = ForeignKey.NO_ACTION
    )])
data class Compra(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val cantidad: Int,
    val costoUnitario: Double,
    val total: Double,
    val timestamp: String
)