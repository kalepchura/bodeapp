package com.bodeapp.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bodeapp.model.Venta;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VentaDao_Impl implements VentaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Venta> __insertionAdapterOfVenta;

  public VentaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVenta = new EntityInsertionAdapter<Venta>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Venta` (`id`,`productoId`,`cantidad`,`precioUnitario`,`total`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Venta entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductoId());
        statement.bindLong(3, entity.getCantidad());
        statement.bindDouble(4, entity.getPrecioUnitario());
        statement.bindDouble(5, entity.getTotal());
        if (entity.getTimestamp() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTimestamp());
        }
      }
    };
  }

  @Override
  public Object insertVenta(final Venta venta, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfVenta.insertAndReturnId(venta);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertVentaAndReduceStock(final AppDatabase db, final Venta venta,
      final int cantidad, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> VentaDao.DefaultImpls.insertVentaAndReduceStock(VentaDao_Impl.this, db, venta, cantidad, __cont), $completion);
  }

  @Override
  public Flow<List<Venta>> ventasPorFecha(final String fecha) {
    final String _sql = "SELECT * FROM Venta WHERE date(timestamp) = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fecha == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fecha);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"Venta"}, new Callable<List<Venta>>() {
      @Override
      @NonNull
      public List<Venta> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductoId = CursorUtil.getColumnIndexOrThrow(_cursor, "productoId");
          final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
          final int _cursorIndexOfPrecioUnitario = CursorUtil.getColumnIndexOrThrow(_cursor, "precioUnitario");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Venta> _result = new ArrayList<Venta>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Venta _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpProductoId;
            _tmpProductoId = _cursor.getInt(_cursorIndexOfProductoId);
            final int _tmpCantidad;
            _tmpCantidad = _cursor.getInt(_cursorIndexOfCantidad);
            final double _tmpPrecioUnitario;
            _tmpPrecioUnitario = _cursor.getDouble(_cursorIndexOfPrecioUnitario);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpTimestamp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmpTimestamp = null;
            } else {
              _tmpTimestamp = _cursor.getString(_cursorIndexOfTimestamp);
            }
            _item = new Venta(_tmpId,_tmpProductoId,_tmpCantidad,_tmpPrecioUnitario,_tmpTotal,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Venta>> ventasDelDia() {
    final String _sql = "SELECT * FROM Venta WHERE date(timestamp) = date('now', 'localtime') ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"Venta"}, new Callable<List<Venta>>() {
      @Override
      @NonNull
      public List<Venta> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductoId = CursorUtil.getColumnIndexOrThrow(_cursor, "productoId");
          final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
          final int _cursorIndexOfPrecioUnitario = CursorUtil.getColumnIndexOrThrow(_cursor, "precioUnitario");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Venta> _result = new ArrayList<Venta>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Venta _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpProductoId;
            _tmpProductoId = _cursor.getInt(_cursorIndexOfProductoId);
            final int _tmpCantidad;
            _tmpCantidad = _cursor.getInt(_cursorIndexOfCantidad);
            final double _tmpPrecioUnitario;
            _tmpPrecioUnitario = _cursor.getDouble(_cursorIndexOfPrecioUnitario);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpTimestamp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmpTimestamp = null;
            } else {
              _tmpTimestamp = _cursor.getString(_cursorIndexOfTimestamp);
            }
            _item = new Venta(_tmpId,_tmpProductoId,_tmpCantidad,_tmpPrecioUnitario,_tmpTotal,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object totalPorFecha(final String fecha, final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(total) FROM Venta WHERE date(timestamp) = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fecha == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fecha);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object totalDelDia(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(total) FROM Venta WHERE date(timestamp) = date('now', 'localtime')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
