package com.example.benha.fyp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FlashRepository {

    private FlashDao fDao;
    private List<Flashcard> allCards;
    private LiveData<List<Flashcard>> liveCards;

    FlashRepository(Application application){
        FlashcardDatabase db = FlashcardDatabase.getDatabase(application);
        fDao = db.flashDao();
        allCards = fDao.getAllFlashcards();
        liveCards = fDao.getLiveFlashcards();
    }

    List<Flashcard> getAllFlashcards(){
            return allCards;
    }

    LiveData<List<Flashcard>> getLiveFlashcards() { return liveCards;}

    void deleteCard(int index){deleteCard(index);}

    public void insert (Flashcard flashcard){
        new insertAsyncTask(fDao).execute(flashcard);
    }

    private static class insertAsyncTask extends AsyncTask<Flashcard, Void, Void>{
        private FlashDao asyncDao;

        insertAsyncTask(FlashDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Flashcard... params){
            asyncDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll(){
        fDao.deleteAll();
    }

}
