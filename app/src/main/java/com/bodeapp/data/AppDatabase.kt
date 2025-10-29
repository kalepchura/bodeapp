package com.bodeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bodeapp.model.Compra
import com.bodeapp.model.Producto
import com.bodeapp.model.Venta

@Database(
    entities = [Producto::class, Venta::class, Compra::class],
    version = 2, // incrementado por cambio de esquema
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun ventaDao(): VentaDao
    abstract fun compraDao(): CompraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bodeapp_db"
                )
                    .fallbackToDestructiveMigration() // borra DB antigua si hay conflicto
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
