package com.example.benha.fyp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

//view model allows for all ui interaction to come from one place
public class FlashcardViewModel extends AndroidViewModel {

    private FlashRepository fRep;
    private List<Flashcard> mAllFlashcard;
    private LiveData<List<Flashcard>> liveCards;

    public FlashcardViewModel(Application application){
        super(application);
        fRep = new FlashRepository(application);
        mAllFlashcard = fRep.getAllFlashcards();
        liveCards = fRep.getLiveFlashcards();
    }

    LiveData<List<Flashcard>> getLiveCards() {return liveCards;}

    List<Flashcard> getAllFlashcards() {
        return mAllFlashcard;
    }

    public void insert(Flashcard flashcard){
        fRep.insert(flashcard);
    }

    public void updateScore(int index, int newScore){fRep.updateScore(index, newScore);}

    public int returnScore(int index){
        Log.d("Test 1449", ""+fRep.returnScore(index));
        return fRep.returnScore(index);}

    public void deleteAll(){
        fRep.deleteAll();
    }

    public void deleteCard(int index){fRep.deleteCard(index);}

    public void resetScore(int index){fRep.resetScore(index);}
}
