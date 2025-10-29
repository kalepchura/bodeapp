package com.bodeapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000e0\rH\'J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u000e0\rH\'J\u001e\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0019"}, d2 = {"Lcom/bodeapp/data/ProductoDao;", "", "decrementStock", "", "id", "", "cantidad", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteById", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findById", "Lcom/bodeapp/model/Producto;", "getActivos", "Lkotlinx/coroutines/flow/Flow;", "", "getAll", "incrementStock", "insert", "", "producto", "(Lcom/bodeapp/model/Producto;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markInactive", "setStock", "newStock", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface ProductoDao {
    
    /**
     * Obtiene todos los productos (activos e inactivos)
     */
    @androidx.room.Query(value = "SELECT * FROM Producto ORDER BY nombre ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Producto>> getAll();
    
    /**
     * Obtiene solo productos activos (activo = 1)
     */
    @androidx.room.Query(value = "SELECT * FROM Producto WHERE activo = 1 ORDER BY nombre ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bodeapp.model.Producto>> getActivos();
    
    /**
     * Busca un producto por ID
     * Retorna null si no existe
     */
    @androidx.room.Query(value = "SELECT * FROM Producto WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bodeapp.model.Producto> $completion);
    
    /**
     * Inserta un nuevo producto
     * Retorna el ID generado
     */
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Producto producto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Actualiza un producto existente
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Producto producto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Elimina un producto por ID (hard delete)
     */
    @androidx.room.Query(value = "DELETE FROM Producto WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Marca un producto como inactivo (soft delete)
     */
    @androidx.room.Query(value = "UPDATE Producto SET activo = 0 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markInactive(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Actualiza el stock de un producto
     * Usado por transacciones de ventas y compras
     */
    @androidx.room.Query(value = "UPDATE Producto SET stock = :newStock WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setStock(int id, int newStock, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Incrementa el stock de un producto
     * Útil para compras
     */
    @androidx.room.Query(value = "UPDATE Producto SET stock = stock + :cantidad WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object incrementStock(int id, int cantidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Decrementa el stock de un producto
     * Útil para ventas (validar antes que stock >= cantidad)
     */
    @androidx.room.Query(value = "UPDATE Producto SET stock = stock - :cantidad WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object decrementStock(int id, int cantidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}