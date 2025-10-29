package com.bodeapp.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProductoDao _productoDao;

  private volatile VentaDao _ventaDao;

  private volatile CompraDao _compraDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `Producto` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `precioUnitario` REAL NOT NULL, `stock` INTEGER NOT NULL, `activo` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Venta` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productoId` INTEGER NOT NULL, `cantidad` INTEGER NOT NULL, `precioUnitario` REAL NOT NULL, `total` REAL NOT NULL, `timestamp` TEXT NOT NULL, FOREIGN KEY(`productoId`) REFERENCES `Producto`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Compra` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productoId` INTEGER NOT NULL, `cantidad` INTEGER NOT NULL, `costoUnitario` REAL NOT NULL, `total` REAL NOT NULL, `timestamp` TEXT NOT NULL, FOREIGN KEY(`productoId`) REFERENCES `Producto`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b4caf7e1a99139a2599c07e64825691f')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `Producto`");
        db.execSQL("DROP TABLE IF EXISTS `Venta`");
        db.execSQL("DROP TABLE IF EXISTS `Compra`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProducto = new HashMap<String, TableInfo.Column>(5);
        _columnsProducto.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("precioUnitario", new TableInfo.Column("precioUnitario", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("stock", new TableInfo.Column("stock", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducto = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducto = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducto = new TableInfo("Producto", _columnsProducto, _foreignKeysProducto, _indicesProducto);
        final TableInfo _existingProducto = TableInfo.read(db, "Producto");
        if (!_infoProducto.equals(_existingProducto)) {
          return new RoomOpenHelper.ValidationResult(false, "Producto(com.bodeapp.model.Producto).\n"
                  + " Expected:\n" + _infoProducto + "\n"
                  + " Found:\n" + _existingProducto);
        }
        final HashMap<String, TableInfo.Column> _columnsVenta = new HashMap<String, TableInfo.Column>(6);
        _columnsVenta.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVenta.put("productoId", new TableInfo.Column("productoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVenta.put("cantidad", new TableInfo.Column("cantidad", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVenta.put("precioUnitario", new TableInfo.Column("precioUnitario", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVenta.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVenta.put("timestamp", new TableInfo.Column("timestamp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVenta = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVenta.add(new TableInfo.ForeignKey("Producto", "NO ACTION", "NO ACTION", Arrays.asList("productoId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVenta = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVenta = new TableInfo("Venta", _columnsVenta, _foreignKeysVenta, _indicesVenta);
        final TableInfo _existingVenta = TableInfo.read(db, "Venta");
        if (!_infoVenta.equals(_existingVenta)) {
          return new RoomOpenHelper.ValidationResult(false, "Venta(com.bodeapp.model.Venta).\n"
                  + " Expected:\n" + _infoVenta + "\n"
                  + " Found:\n" + _existingVenta);
        }
        final HashMap<String, TableInfo.Column> _columnsCompra = new HashMap<String, TableInfo.Column>(6);
        _columnsCompra.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompra.put("productoId", new TableInfo.Column("productoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompra.put("cantidad", new TableInfo.Column("cantidad", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompra.put("costoUnitario", new TableInfo.Column("costoUnitario", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompra.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompra.put("timestamp", new TableInfo.Column("timestamp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCompra = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCompra.add(new TableInfo.ForeignKey("Producto", "NO ACTION", "NO ACTION", Arrays.asList("productoId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCompra = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCompra = new TableInfo("Compra", _columnsCompra, _foreignKeysCompra, _indicesCompra);
        final TableInfo _existingCompra = TableInfo.read(db, "Compra");
        if (!_infoCompra.equals(_existingCompra)) {
          return new RoomOpenHelper.ValidationResult(false, "Compra(com.bodeapp.model.Compra).\n"
                  + " Expected:\n" + _infoCompra + "\n"
                  + " Found:\n" + _existingCompra);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b4caf7e1a99139a2599c07e64825691f", "489241a31631ae74282c799102fadc83");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Producto","Venta","Compra");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `Venta`");
      _db.execSQL("DELETE FROM `Compra`");
      _db.execSQL("DELETE FROM `Producto`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProductoDao.class, ProductoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VentaDao.class, VentaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CompraDao.class, CompraDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProductoDao productoDao() {
    if (_productoDao != null) {
      return _productoDao;
    } else {
      synchronized(this) {
        if(_productoDao == null) {
          _productoDao = new ProductoDao_Impl(this);
        }
        return _productoDao;
      }
    }
  }

  @Override
  public VentaDao ventaDao() {
    if (_ventaDao != null) {
      return _ventaDao;
    } else {
      synchronized(this) {
        if(_ventaDao == null) {
          _ventaDao = new VentaDao_Impl(this);
        }
        return _ventaDao;
      }
    }
  }

  @Override
  public CompraDao compraDao() {
    if (_compraDao != null) {
      return _compraDao;
    } else {
      synchronized(this) {
        if(_compraDao == null) {
          _compraDao = new CompraDao_Impl(this);
        }
        return _compraDao;
      }
    }
  }
}
