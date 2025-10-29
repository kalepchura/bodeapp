package com.bodeapp.ui.screen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u001a,\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0007\u001a$\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00060\u0010H\u0007\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\u00a8\u0006\u0012"}, d2 = {"GreenAvailable", "Landroidx/compose/ui/graphics/Color;", "J", "LightGray", "TealPrimary", "ProductCard", "", "producto", "Lcom/bodeapp/model/Producto;", "onEdit", "Lkotlin/Function0;", "onDelete", "ProductsScreen", "db", "Lcom/bodeapp/data/AppDatabase;", "onNavigate", "Lkotlin/Function1;", "", "app_debug"})
public final class ProductsScreenKt {
    private static final long TealPrimary = 0L;
    private static final long GreenAvailable = 0L;
    private static final long LightGray = 0L;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProductsScreen(@org.jetbrains.annotations.NotNull()
    com.bodeapp.data.AppDatabase db, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    /**
     * Card individual de producto según el diseño de la imagen 1
     * Muestra: Nombre, Precio, Stock y badge "Disponible"
     */
    @androidx.compose.runtime.Composable()
    public static final void ProductCard(@org.jetbrains.annotations.NotNull()
    com.bodeapp.model.Producto producto, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
}