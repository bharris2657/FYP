package com.example.benha.fyp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//this defines the table flashcard which is contained in the database
@Entity(tableName = "flashcard_table")
public class Flashcard {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "indexValue")
    private int indexValue;

    @ColumnInfo(name = "questionText")
    private String qText;

    @ColumnInfo(name = "answerText")
    private String aText;

    @ColumnInfo(name = "score")
    private int score;


    public Flashcard(){}

    @Ignore
    public Flashcard(String nQuestion, String nAnswer){
        qText = nQuestion;
        aText = nAnswer;
    }

    @NonNull
    public int getIndexValue() {
        return indexValue;
    }

    public String getQText() {
        return qText;
    }

    public String getAText() {
        return aText;
    }

    public int getScore() {
        return score;
    }

    public void setIndexValue(@NonNull int index) {
        this.indexValue = index;
    }

    public void setQText(String qText) {this.qText = qText;}

    public void setAText(String aText) {
        this.aText = aText;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
