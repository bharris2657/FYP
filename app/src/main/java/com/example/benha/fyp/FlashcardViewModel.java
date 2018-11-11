package com.example.benha.fyp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import java.util.List;

//view model allows for all ui interaction to come from one place
public class FlashcardViewModel extends AndroidViewModel {

    private FlashRepository fRep;
    private List<Flashcard> mAllFlashcard;

    public FlashcardViewModel(Application application){
        super(application);
        fRep = new FlashRepository(application);
        mAllFlashcard = fRep.getAllFlashcards();
    }

    List<Flashcard> getAllFlashcards() {
        return mAllFlashcard;
    }

    public void insert(Flashcard flashcard){
        fRep.insert(flashcard);
    }

    public void deleteAll(){
        fRep.deleteAll();
    }
}
