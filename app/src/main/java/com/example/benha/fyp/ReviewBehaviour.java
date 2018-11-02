package com.example.benha.fyp;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewBehaviour extends Fragment implements View.OnClickListener {

    private Flashcard flashcard = new Flashcard(1, 1);

    public ReviewBehaviour() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_review_behaviour,container,false);

        TextView questionText = (TextView) view.findViewById(R.id.card);
        questionText.setText(flashcard.getQuestion(0));

        Button easyButton = (Button) view.findViewById(R.id.easyButton);
        easyButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        flashcard.saveScore(0, 5);
        flashcard.saveScore(1, 5);
        flashcard.saveScore(2, 5);
    }


}
