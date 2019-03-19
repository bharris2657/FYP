package com.example.benha.fyp;


import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnBehaviour extends Fragment implements View.OnClickListener{

    private int flashcardIndex = 0;
    private int databaseIndex;

    public LearnBehaviour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_behaviour, container, false);

        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        TextView card = view.findViewById(R.id.learnCard);
        TextView answer = view.findViewById(R.id.learnAnswer);
        List<Flashcard> learnCards = fModel.getLearnCards();
        if(learnCards.isEmpty()){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new Learn()).commit();
        }
        else{
            initialCard(learnCards, card, answer);
        }
        Button easyLearn = view.findViewById(R.id.easyLearn);
        Button mediumLearn = view.findViewById(R.id.mediumLearn);
        Button hardLearn = view.findViewById(R.id.hardLearn);
        easyLearn.setOnClickListener(this);
        mediumLearn.setOnClickListener(this);
        hardLearn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> fList =  fModel.getLearnCards();
        newCard(flashcardIndex, fList, fModel, getView(),  id);
    }

    public void newCard(int index, List<Flashcard> f, FlashcardViewModel fm, View v, int id){
        TextView card = v.findViewById(R.id.learnCard);
        TextView answer = v.findViewById(R.id.learnAnswer);
        if(f.isEmpty()){
            endList();
            return;
        }
        databaseIndex = f.get(0).getIndexValue();
        switch (id){
            case R.id.easyLearn:
                fm.updateScore(databaseIndex, 15);
                break;
            case R.id.mediumLearn:
                fm.updateScore(databaseIndex, 10);
                break;
            case R.id.hardLearn:
                fm.updateScore(databaseIndex, 5);
                break;
        }
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        f = fModel.getLearnCards();
        //checks if it's out of bounds
        if(f.isEmpty()){
            endList();
            return;
        }
        card.setText(f.get(0).getQText());
        answer.setText(f.get(0).getAText());
    }

    public void endList(){
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new Learn()).commit();
    }

    public void initialCard(List<Flashcard> f, TextView question, TextView answer){
        question.setText(f.get(0).getQText());
        answer.setText(f.get(0).getAText());
    }

}
