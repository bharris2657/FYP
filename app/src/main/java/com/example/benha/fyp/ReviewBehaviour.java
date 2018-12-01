package com.example.benha.fyp;


import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewBehaviour extends Fragment implements View.OnClickListener {

    private int flashcardIndex = 0;
    private int databaseIndex;

    public ReviewBehaviour() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //gets Application to use to start fModel
        View view = inflater.inflate(R.layout.fragment_review_behaviour, container, false);
        //get application in order to create a flashcard view model
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        //load a local list of flashcards
        List<Flashcard> fList =  fModel.getReviewCards();
        //set the initial text to the question text
        TextView questionText = view.findViewById(R.id.card);
        if(fList.isEmpty()){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new Review()).commit();
        }
        else{
            initialCard(fList, questionText);
            databaseIndex = fList.get(flashcardIndex).getIndexValue();
        }
        Button continueButton = view.findViewById(R.id.continueButton);
        Button easyButton = view.findViewById(R.id.easyButton);
        Button mediumButton = view.findViewById(R.id.mediumButton);
        Button hardButton = view.findViewById(R.id.hardButton);
        Button incButton = view.findViewById(R.id.incorrectButton);
        continueButton.setOnClickListener(this);
        easyButton.setOnClickListener(this);
        mediumButton.setOnClickListener(this);
        hardButton.setOnClickListener(this);
        incButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        TextView questionText = getView().findViewById(R.id.card);
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> fList =  fModel.getReviewCards();

        int id = v.getId();
        if(id == R.id.continueButton){
            continuePressed(flashcardIndex, fList, fModel, questionText);
        }else{
            newCard(flashcardIndex, fList, fModel, questionText, id);
        }

    }

    public void continuePressed(int index, List<Flashcard> f, FlashcardViewModel fm, TextView text){
        //get layout which contains difficulty buttons in order to set visibility
        LinearLayout buttons = getView().findViewById(R.id.buttonContainer);
        Button continueButton = getView().findViewById(R.id.continueButton);
        continueButton.setVisibility(GONE);
        buttons.setVisibility(View.VISIBLE);
        text.setText(f.get(index).getAText());
    }

    public void newCard(int index, List<Flashcard> f, FlashcardViewModel fm, TextView text, int viewId){
        LinearLayout buttons = getView().findViewById(R.id.buttonContainer);
        Button continueButton = getView().findViewById(R.id.continueButton);
        //sets the local database index to update scores
        databaseIndex = f.get(index).getIndexValue();
        switch (viewId){
            case R.id.easyButton:
                fm.updateScore(databaseIndex, 15);
                break;
            case R.id.mediumButton:
                fm.updateScore(databaseIndex, 10);
                break;
            case R.id.hardButton:
                fm.updateScore(databaseIndex, 5);
                break;
            case R.id.incorrectButton:
                fm.resetScore(databaseIndex);
                break;
        }
        //increments the flashcard list index
        flashcardIndex++;
        //checks if it's out of bounds
        if(flashcardIndex == f.size()){
            endList();
            return;
        }
        //sets the new question text
        text.setText(f.get(flashcardIndex).getQText());
        //changes buttons over
        buttons.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.VISIBLE);

    }

    public void endList(){
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new Review()).commit();
    }

    public void initialCard(List<Flashcard> f, TextView text){
        text.setText(f.get(0).getQText());
    }

}
