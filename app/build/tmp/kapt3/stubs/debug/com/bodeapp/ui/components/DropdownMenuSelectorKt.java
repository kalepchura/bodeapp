package com.bodeapp.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aE\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0014\u0010\u000b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0004\u0012\u00020\u00050\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u00a2\u0006\u0002\u0010\u000f\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\u00a8\u0006\u0010"}, d2 = {"LightGray", "Landroidx/compose/ui/graphics/Color;", "J", "TealPrimary", "DropdownMenuSelector", "", "productos", "", "Lcom/bodeapp/model/Producto;", "selectedId", "", "onSelect", "Lkotlin/Function1;", "modifier", "Landroidx/compose/ui/Modifier;", "(Ljava/util/List;Ljava/lang/Integer;Lkotlin/jvm/functions/Function1;Landroidx/compose/ui/Modifier;)V", "app_debug"})
public final class DropdownMenuSelectorKt {
    private static final long TealPrimary = 0L;
    private static final long LightGray = 0L;
    
    /**
     * Componente Dropdown para seleccionar productos
     * Diseño según la imagen de SalesScreen
     *
     * @param productos Lista de productos disponibles
     * @param selectedId ID del producto seleccionado (null si no hay selección)
     * @param onSelect Callback cuando se selecciona un producto
     * @param modifier Modificador opcional
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DropdownMenuSelector(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bodeapp.model.Producto> productos, @org.jetbrains.annotations.Nullable()
    java.lang.Integer selectedId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onSelect, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}