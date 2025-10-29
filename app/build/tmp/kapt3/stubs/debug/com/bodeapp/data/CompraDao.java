package com.bodeapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\tJ&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0097@\u00a2\u0006\u0002\u0010\u0010J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/bodeapp/data/CompraDao;", "", "allCompras", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bodeapp/model/Compra;", "insertCompra", "", "compra", "(Lcom/bodeapp/model/Compra;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCompraAndIncreaseStock", "", "db", "Lcom/bodeapp/data/AppDatabase;", "cantidad", "", "(Lcom/bodeapp/data/AppDatabase;Lcom/bodeapp/model/Compra;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "totalPorFecha", "", "fecha", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface CompraDao {
    
    @androidx.room.Query(value = "SELECT * FROM Compra ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Compra>> allCompras();
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertCompra(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Compra compra, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertCompraAndIncreaseStock(@org.jetbrains.annotations.NotNull()
    com.bodeapp.data.AppDatabase db, @org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Compra compra, int cantidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(total) FROM Compra WHERE date(timestamp) = :fecha")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object totalPorFecha(@org.jetbrains.annotations.NotNull()
    java.lang.String fecha, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object insertCompraAndIncreaseStock(@org.jetbrains.annotations.NotNull()
        com.bodeapp.data.CompraDao $this, @org.jetbrains.annotations.NotNull()
        com.bodeapp.data.AppDatabase db, @org.jetbrains.annotations.NotNull()
        com.bodeapp.model.Compra compra, int cantidad, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}