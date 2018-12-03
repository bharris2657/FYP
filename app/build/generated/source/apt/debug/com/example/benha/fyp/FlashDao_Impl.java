package com.example.benha.fyp;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class FlashDao_Impl implements FlashDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfFlashcard;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItem;

  private final SharedSQLiteStatement __preparedStmtOfUpdateScore;

  private final SharedSQLiteStatement __preparedStmtOfResetScore;

  public FlashDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlashcard = new EntityInsertionAdapter<Flashcard>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `flashcard_table`(`indexValue`,`questionText`,`answerText`,`score`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Flashcard value) {
        stmt.bindLong(1, value.getIndexValue());
        if (value.getQText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getQText());
        }
        if (value.getAText() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAText());
        }
        stmt.bindLong(4, value.getScore());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM flashcard_table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteItem = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM flashcard_table WHERE indexValue = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateScore = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE flashcard_table SET score = score+? WHERE indexValue = ?";
        return _query;
      }
    };
    this.__preparedStmtOfResetScore = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE flashcard_table SET score = 0 WHERE indexValue = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(Flashcard flashcard) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfFlashcard.insert(flashcard);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteItem(int newIndex) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItem.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, newIndex);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteItem.release(_stmt);
    }
  }

  @Override
  public void updateScore(int newIndex, int newScore) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateScore.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, newScore);
      _argIndex = 2;
      _stmt.bindLong(_argIndex, newIndex);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateScore.release(_stmt);
    }
  }

  @Override
  public void resetScore(int newIndex) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfResetScore.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, newIndex);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfResetScore.release(_stmt);
    }
  }

  @Override
  public List<Flashcard> getAllFlashcards() {
    final String _sql = "SELECT * from flashcard_table ORDER BY indexValue ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIndexValue = _cursor.getColumnIndexOrThrow("indexValue");
      final int _cursorIndexOfQText = _cursor.getColumnIndexOrThrow("questionText");
      final int _cursorIndexOfAText = _cursor.getColumnIndexOrThrow("answerText");
      final int _cursorIndexOfScore = _cursor.getColumnIndexOrThrow("score");
      final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Flashcard _item;
        _item = new Flashcard();
        final int _tmpIndexValue;
        _tmpIndexValue = _cursor.getInt(_cursorIndexOfIndexValue);
        _item.setIndexValue(_tmpIndexValue);
        final String _tmpQText;
        _tmpQText = _cursor.getString(_cursorIndexOfQText);
        _item.setQText(_tmpQText);
        final String _tmpAText;
        _tmpAText = _cursor.getString(_cursorIndexOfAText);
        _item.setAText(_tmpAText);
        final int _tmpScore;
        _tmpScore = _cursor.getInt(_cursorIndexOfScore);
        _item.setScore(_tmpScore);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Flashcard>> getLiveFlashcards() {
    final String _sql = "SELECT * from flashcard_table ORDER BY indexValue ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Flashcard>>() {
      private Observer _observer;

      @Override
      protected List<Flashcard> compute() {
        if (_observer == null) {
          _observer = new Observer("flashcard_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfIndexValue = _cursor.getColumnIndexOrThrow("indexValue");
          final int _cursorIndexOfQText = _cursor.getColumnIndexOrThrow("questionText");
          final int _cursorIndexOfAText = _cursor.getColumnIndexOrThrow("answerText");
          final int _cursorIndexOfScore = _cursor.getColumnIndexOrThrow("score");
          final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Flashcard _item;
            _item = new Flashcard();
            final int _tmpIndexValue;
            _tmpIndexValue = _cursor.getInt(_cursorIndexOfIndexValue);
            _item.setIndexValue(_tmpIndexValue);
            final String _tmpQText;
            _tmpQText = _cursor.getString(_cursorIndexOfQText);
            _item.setQText(_tmpQText);
            final String _tmpAText;
            _tmpAText = _cursor.getString(_cursorIndexOfAText);
            _item.setAText(_tmpAText);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            _item.setScore(_tmpScore);
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
    }.getLiveData();
  }

  @Override
  public int returnScore(int newIndex) {
    final String _sql = "SELECT score FROM flashcard_table WHERE indexValue = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, newIndex);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int returnIndex(int newIndex) {
    final String _sql = "SELECT indexValue FROM flashcard_table WHERE indexValue = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, newIndex);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Flashcard> getLearnCards() {
    final String _sql = "SELECT * FROM flashcard_table where score == 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIndexValue = _cursor.getColumnIndexOrThrow("indexValue");
      final int _cursorIndexOfQText = _cursor.getColumnIndexOrThrow("questionText");
      final int _cursorIndexOfAText = _cursor.getColumnIndexOrThrow("answerText");
      final int _cursorIndexOfScore = _cursor.getColumnIndexOrThrow("score");
      final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Flashcard _item;
        _item = new Flashcard();
        final int _tmpIndexValue;
        _tmpIndexValue = _cursor.getInt(_cursorIndexOfIndexValue);
        _item.setIndexValue(_tmpIndexValue);
        final String _tmpQText;
        _tmpQText = _cursor.getString(_cursorIndexOfQText);
        _item.setQText(_tmpQText);
        final String _tmpAText;
        _tmpAText = _cursor.getString(_cursorIndexOfAText);
        _item.setAText(_tmpAText);
        final int _tmpScore;
        _tmpScore = _cursor.getInt(_cursorIndexOfScore);
        _item.setScore(_tmpScore);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Flashcard> getReviewCards() {
    final String _sql = "SELECT * FROM flashcard_table where score > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIndexValue = _cursor.getColumnIndexOrThrow("indexValue");
      final int _cursorIndexOfQText = _cursor.getColumnIndexOrThrow("questionText");
      final int _cursorIndexOfAText = _cursor.getColumnIndexOrThrow("answerText");
      final int _cursorIndexOfScore = _cursor.getColumnIndexOrThrow("score");
      final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Flashcard _item;
        _item = new Flashcard();
        final int _tmpIndexValue;
        _tmpIndexValue = _cursor.getInt(_cursorIndexOfIndexValue);
        _item.setIndexValue(_tmpIndexValue);
        final String _tmpQText;
        _tmpQText = _cursor.getString(_cursorIndexOfQText);
        _item.setQText(_tmpQText);
        final String _tmpAText;
        _tmpAText = _cursor.getString(_cursorIndexOfAText);
        _item.setAText(_tmpAText);
        final int _tmpScore;
        _tmpScore = _cursor.getInt(_cursorIndexOfScore);
        _item.setScore(_tmpScore);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
