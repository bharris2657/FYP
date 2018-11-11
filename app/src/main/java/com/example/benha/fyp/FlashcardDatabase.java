package com.example.benha.fyp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {Flashcard.class}, version = 2)
public abstract class FlashcardDatabase extends RoomDatabase {

    public abstract FlashDao flashDao();

    private static volatile  FlashcardDatabase INSTANCE;

    //this is the main database
    static FlashcardDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (FlashcardDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    FlashcardDatabase.class, "flashcard_database")
                            .addMigrations(MIGRATION_1_2)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //enables persistent data
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

}
