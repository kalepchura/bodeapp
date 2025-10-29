package com.bodeapp.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001bJ&\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%H\u0086@\u00a2\u0006\u0002\u0010&J\u0016\u0010\'\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%H\u0086@\u00a2\u0006\u0002\u0010&J\u0016\u0010(\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0016R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\nR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\n\u00a8\u0006)"}, d2 = {"Lcom/bodeapp/repository/BodegaRepository;", "", "db", "Lcom/bodeapp/data/AppDatabase;", "(Lcom/bodeapp/data/AppDatabase;)V", "comprasAll", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bodeapp/model/Compra;", "getComprasAll", "()Lkotlinx/coroutines/flow/Flow;", "productosActivos", "Lcom/bodeapp/model/Producto;", "getProductosActivos", "productosAll", "getProductosAll", "ventasDelDia", "Lcom/bodeapp/model/Venta;", "getVentasDelDia", "addProducto", "", "producto", "(Lcom/bodeapp/model/Producto;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOrMarkProducto", "", "productoId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerCompra", "cantidad", "costoUnitario", "", "(IIDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerVenta", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "totalComprasPorFecha", "fechaISO", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "totalVentasPorFecha", "updateProducto", "app_debug"})
public final class BodegaRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bodeapp.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Producto>> productosActivos = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Producto>> productosAll = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Venta>> ventasDelDia = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Compra>> comprasAll = null;
    
    public BodegaRepository(@org.jetbrains.annotations.NotNull()
    com.bodeapp.data.AppDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Producto>> getProductosActivos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Producto>> getProductosAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Venta>> getVentasDelDia() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Compra>> getComprasAll() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addProducto(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Producto producto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProducto(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Producto producto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteOrMarkProducto(int productoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object registerVenta(int productoId, int cantidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object registerCompra(int productoId, int cantidad, double costoUnitario, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object totalVentasPorFecha(@org.jetbrains.annotations.NotNull()
    java.lang.String fechaISO, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object totalComprasPorFecha(@org.jetbrains.annotations.NotNull()
    java.lang.String fechaISO, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
}