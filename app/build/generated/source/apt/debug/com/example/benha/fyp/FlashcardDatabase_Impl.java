package com.example.benha.fyp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class FlashcardDatabase_Impl extends FlashcardDatabase {
  private volatile FlashDao _flashDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `flashcard_table` (`indexValue` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `questionText` TEXT, `answerText` TEXT, `score` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"59b1d288dba442e04ba464609b875a9f\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `flashcard_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFlashcardTable = new HashMap<String, TableInfo.Column>(4);
        _columnsFlashcardTable.put("indexValue", new TableInfo.Column("indexValue", "INTEGER", true, 1));
        _columnsFlashcardTable.put("questionText", new TableInfo.Column("questionText", "TEXT", false, 0));
        _columnsFlashcardTable.put("answerText", new TableInfo.Column("answerText", "TEXT", false, 0));
        _columnsFlashcardTable.put("score", new TableInfo.Column("score", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFlashcardTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFlashcardTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFlashcardTable = new TableInfo("flashcard_table", _columnsFlashcardTable, _foreignKeysFlashcardTable, _indicesFlashcardTable);
        final TableInfo _existingFlashcardTable = TableInfo.read(_db, "flashcard_table");
        if (! _infoFlashcardTable.equals(_existingFlashcardTable)) {
          throw new IllegalStateException("Migration didn't properly handle flashcard_table(com.example.benha.fyp.Flashcard).\n"
                  + " Expected:\n" + _infoFlashcardTable + "\n"
                  + " Found:\n" + _existingFlashcardTable);
        }
      }
    }, "59b1d288dba442e04ba464609b875a9f", "61a598429ed959bafdbfefdc6628bc36");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "flashcard_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `flashcard_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public FlashDao flashDao() {
    if (_flashDao != null) {
      return _flashDao;
    } else {
      synchronized(this) {
        if(_flashDao == null) {
          _flashDao = new FlashDao_Impl(this);
        }
        return _flashDao;
      }
    }
  }
}
