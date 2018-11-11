package com.example.benha.fyp;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private FlashcardDatabase fCardDb;

    public ReviewBehaviour() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //gets Application to use to start fModel
        View view = inflater.inflate(R.layout.fragment_review_behaviour, container, false);
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> fList =  fModel.getAllFlashcards();
        TextView testText = view.findViewById(R.id.card);
        testText.setText(fList.get(0).getQText());
        Button continueButton = view.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        //All Testing Stuff
        TextView testText = getView().findViewById(R.id.card);
        TextView testText1 = getView().findViewById(R.id.scoreTracker);
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> fList =  fModel.getAllFlashcards();

        int id = v.getId();
        if(id == R.id.continueButton){
            testText.setText(fList.get(0).getAText());
            testText1.setText(""+fList.size());
        }
    }


    public void showAnswer(Flashcard f){

    }



}
