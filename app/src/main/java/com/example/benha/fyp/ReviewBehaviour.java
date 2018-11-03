package com.example.benha.fyp;


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

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewBehaviour extends Fragment implements View.OnClickListener {

    private ArrayList<Flashcard> flashcards = populateFlashcardArray();
    private Flashcard currentCard = flashcards.get(0);
    private int cardCount = 0;

    public ReviewBehaviour() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_behaviour, container, false);
        TextView text = (TextView) view.findViewById(R.id.card);
        text.setText(currentCard.getQuestion());
        Button easyButton = (Button) view.findViewById(R.id.easyButton);
        Button mediumButton = (Button) view.findViewById(R.id.mediumButton);
        Button hardButton = (Button) view.findViewById(R.id.hardButton);
        Button incorrectButton = (Button) view.findViewById(R.id.incorrectButton);
        Button contineButton = (Button) view.findViewById(R.id.continueButton);
        contineButton.setOnClickListener(this);
        easyButton.setOnClickListener(this);
        mediumButton.setOnClickListener(this);
        hardButton.setOnClickListener(this);
        incorrectButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.continueButton){
            showAnswer(currentCard);
            Log.d("buttonTesting", "poop");
        }
        if (id == R.id.easyButton) {
            currentCard.saveScore(15);
            currentCard = loadCard(flashcards, cardCount);
            cardCount++;
        }
        if (id == R.id.mediumButton) {
            currentCard.saveScore(10);
            currentCard = loadCard(flashcards, cardCount);
            cardCount++;
        }
        if (id == R.id.hardButton) {
            currentCard.saveScore(5);
            currentCard = loadCard(flashcards, cardCount);
            cardCount++;
        }
        if (id == R.id.incorrectButton) {
            currentCard.saveScore(0);
            currentCard = loadCard(flashcards, cardCount);
            cardCount++;
        }
        if(cardCount > flashcards.size() - 1){
            cardCount = 0;
        }
        //return;
    }
    public Flashcard loadCard(ArrayList<Flashcard> fList, int index){
        //inits all the buttons so they can be made visible/invisible
        TextView text = (TextView) getView().findViewById(R.id.card);
        TextView score = (TextView) getView().findViewById(R.id.scoreTracker);
        Button contineButton = (Button) getView().findViewById(R.id.continueButton);
        Button easyButton = (Button) getView().findViewById(R.id.easyButton);
        Button mediumButton = (Button) getView().findViewById(R.id.mediumButton);
        Button hardButton = (Button) getView().findViewById(R.id.hardButton);
        Button incorrectButton = (Button) getView().findViewById(R.id.incorrectButton);

        String scoreString = String.valueOf(fList.get(index).getScore());
        score.setText(scoreString);
        text.setText(fList.get(index).getAnswer());
        contineButton.setVisibility(getView().VISIBLE);
        easyButton.setVisibility(getView().GONE);
        mediumButton.setVisibility(getView().GONE);
        hardButton.setVisibility(getView().GONE);
        incorrectButton.setVisibility(getView().GONE);

        text.setText(fList.get(index).getQuestion());
        return flashcards.get(index);
    }

    public void showAnswer(Flashcard f){
        //inits all the buttons so they can be made visible/invisible
        TextView text = (TextView) getView().findViewById(R.id.card);
        TextView score = (TextView) getView().findViewById(R.id.scoreTracker);
        Button contineButton = (Button) getView().findViewById(R.id.continueButton);
        Button easyButton = (Button) getView().findViewById(R.id.easyButton);
        Button mediumButton = (Button) getView().findViewById(R.id.mediumButton);
        Button hardButton = (Button) getView().findViewById(R.id.hardButton);
        Button incorrectButton = (Button) getView().findViewById(R.id.incorrectButton);

        String scoreString = String.valueOf(f.getScore());
        score.setText(scoreString);
        contineButton.setVisibility(getView().GONE);
        easyButton.setVisibility(getView().VISIBLE);
        mediumButton.setVisibility(getView().VISIBLE);
        hardButton.setVisibility(getView().VISIBLE);
        incorrectButton.setVisibility(getView().VISIBLE);
        text.setText(f.getAnswer());
    }

    public ArrayList<Flashcard> populateFlashcardArray(){
        File file = new File(Environment.getExternalStorageDirectory(), "cardDatabase.txt");
        ArrayList<Flashcard> flashCardArray = new ArrayList<Flashcard>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            ArrayList<String[]> flashCardString = new ArrayList<String[]>();
            while((line = br.readLine())!= null){
                String[] lineSplit = line.split(";");
                flashCardString.add(lineSplit);
            }
            for(int i = 0 ; i < flashCardString.size() ; i++){
                Flashcard newFlash = new Flashcard(i);
                flashCardArray.add(newFlash);
            }
        }catch(IOException e){

        }
        Log.d("test1815", flashCardArray.toString());
        return flashCardArray;
    }

}
