package com.example.benha.fyp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//Dao provides sql database queries
@Dao
public interface FlashDao {

    @Insert
    void insert(Flashcard flashcard);

    @Query("DELETE FROM flashcard_table")
    void deleteAll();

    @Query("SELECT * from flashcard_table ORDER BY indexValue DESC")
    List<Flashcard> getAllFlashcards();

    @Query("SELECT * from flashcard_table ORDER BY indexValue DESC")
    LiveData<List<Flashcard>> getLiveFlashcards();

    @Query("DELETE FROM flashcard_table WHERE indexValue = :newIndex ")
    void deleteItem(int newIndex);

    @Query("UPDATE flashcard_table SET score = score+:newScore WHERE indexValue = :newIndex")
    void updateScore(int newIndex, int newScore);

    @Query("SELECT score FROM flashcard_table WHERE indexValue = :newIndex")
    int returnScore(int newIndex);

    @Query("SELECT indexValue FROM flashcard_table WHERE indexValue = :newIndex")
    int returnIndex(int newIndex);

}
