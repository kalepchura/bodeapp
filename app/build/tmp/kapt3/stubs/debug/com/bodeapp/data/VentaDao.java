package com.bodeapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J&\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0097@\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00150\u0014H\'J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00150\u00142\u0006\u0010\u0010\u001a\u00020\u0011H\'\u00a8\u0006\u0017"}, d2 = {"Lcom/bodeapp/data/VentaDao;", "", "insertVenta", "", "venta", "Lcom/bodeapp/model/Venta;", "(Lcom/bodeapp/model/Venta;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertVentaAndReduceStock", "", "db", "Lcom/bodeapp/data/AppDatabase;", "cantidad", "", "(Lcom/bodeapp/data/AppDatabase;Lcom/bodeapp/model/Venta;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "totalPorFecha", "", "fecha", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ventasDelDia", "Lkotlinx/coroutines/flow/Flow;", "", "ventasPorFecha", "app_debug"})
@androidx.room.Dao()
public abstract interface VentaDao {
    
    @androidx.room.Query(value = "SELECT * FROM Venta WHERE date(timestamp) = :fecha ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Venta>> ventasPorFecha(@org.jetbrains.annotations.NotNull()
    java.lang.String fecha);
    
    @androidx.room.Query(value = "SELECT * FROM Venta WHERE date(timestamp) = date(\'now\') ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Venta>> ventasDelDia();
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertVenta(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Venta venta, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertVentaAndReduceStock(@org.jetbrains.annotations.NotNull()
    com.bodeapp.data.AppDatabase db, @org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Venta venta, int cantidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(total) FROM Venta WHERE date(timestamp) = :fecha")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object totalPorFecha(@org.jetbrains.annotations.NotNull()
    java.lang.String fecha, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object insertVentaAndReduceStock(@org.jetbrains.annotations.NotNull()
        com.bodeapp.data.VentaDao $this, @org.jetbrains.annotations.NotNull()
        com.bodeapp.data.AppDatabase db, @org.jetbrains.annotations.NotNull()
        com.bodeapp.model.Venta venta, int cantidad, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}