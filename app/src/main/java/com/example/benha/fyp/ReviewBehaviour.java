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
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.generateViewId;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewBehaviour extends Fragment implements View.OnClickListener {

    private int flashcardIndex = 0;
    private int databaseIndex;
    private View view;

    public ReviewBehaviour() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //gets Application to use to start fModel
        view = inflater.inflate(R.layout.fragment_review_behaviour, container, false);
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
        List<Button> buttonList = new ArrayList();
        Button answer1 = view.findViewById(R.id.choice1);
        Button answer2 = view.findViewById(R.id.choice2);
        Button answer3 = view.findViewById(R.id.choice3);
        Button answer4 = view.findViewById(R.id.choice4);
        Button continueButton = view.findViewById(R.id.continueButton);
        Button easyButton = view.findViewById(R.id.easyButton);
        Button mediumButton = view.findViewById(R.id.mediumButton);
        Button hardButton = view.findViewById(R.id.hardButton);
        Button incButton = view.findViewById(R.id.incorrectButton);
        buttonList.add(answer1);
        buttonList.add(answer2);
        buttonList.add(answer3);
        buttonList.add(answer4);
        buttonList.add(continueButton);
        buttonList.add(easyButton);
        buttonList.add(mediumButton);
        buttonList.add(hardButton);
        buttonList.add(incButton);

        for(int i = 0 ; i < buttonList.size() ; i++){
            buttonList.get(i).setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        TextView questionText = getView().findViewById(R.id.card);
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> fList =  fModel.getReviewCards();

        int id = v.getId();
        //checks if the button pressed is a mcq button and will only run continue button if answer is correct
        if(id == R.id.choice1 || id == R.id.choice2 || id == R.id.choice3 || id == R.id.choice4){
            Button button = view.findViewById(id);
            Boolean test = button.getText().toString() == fList.get(flashcardIndex).getAText();
            if(fList.get(flashcardIndex).getAText().equals(button.getText()) || fList.get(flashcardIndex).getQText().equals(button.getText()) ){
                continuePressed(flashcardIndex, fList, fModel, questionText, id);
            }
            else{
                Log.d("Test0230",test.toString());
                Log.d("Test0230",button.getText().toString());
                Log.d("Test0230",fList.get(flashcardIndex).getAText());
                return;
            }
        }
        else if(id == R.id.continueButton){
            continuePressed(flashcardIndex, fList, fModel, questionText, id);
        }
        else{
            newCard(flashcardIndex, fList, fModel, questionText, id);
        }

    }

    public void continuePressed(int index, List<Flashcard> f, FlashcardViewModel fm, TextView text, int id){
        //get layout which contains difficulty buttons in order to set visibility
        LinearLayout buttons = getView().findViewById(R.id.buttonContainer);
        LinearLayout multipleChoiceButtons = view.findViewById(R.id.multipleChoiceButtons);
        multipleChoiceButtons.setVisibility(View.INVISIBLE);
        //checks if it's mcq and if so sets the mcq options to invisible
        if(formatChoice(f.get(flashcardIndex)) == 0){
        }
        Button continueButton = getView().findViewById(R.id.continueButton);
        continueButton.setVisibility(GONE);
        buttons.setVisibility(View.VISIBLE);
        text.setText(f.get(index).getAText());
    }

    public void newCard(int index, List<Flashcard> f, FlashcardViewModel fm, TextView text, int viewId){
        LinearLayout buttons = getView().findViewById(R.id.buttonContainer);
        Button pressedButton = getView().findViewById(viewId);
        //checks if it's out of bounds
        if(f.isEmpty()){
            endList();
            return;
        }

        if(formatChoice(f.get(flashcardIndex)) == 0){
            if(pressedButton.getText() == f.get(flashcardIndex).getAText()){

            }
        }

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
        if(viewId != R.id.incorrectButton){
            flashcardIndex++;
        }
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        f = fModel.getReviewCards();
        if(flashcardIndex == f.size()){
            endList();
            return;
        }
        int format = formatChoice(f.get(flashcardIndex));

        switch (format){
            case  0:
                multipleChoiceEasy(f.get(flashcardIndex).getQText(), f.get(flashcardIndex).getAText());
                break;
            case  1:
                multipleChoiceHard(f.get(flashcardIndex).getQText(), f.get(flashcardIndex).getAText());
                break;
            case  2:
                normalFlashcard(f.get(flashcardIndex).getQText(), f.get(flashcardIndex).getAText());
                break;
        }
        //increments the flashcard list index
        //sets the new question text
        //text.setText(f.get(flashcardIndex).getQText());
        //changes buttons over
        buttons.setVisibility(View.INVISIBLE);

    }

    public void endList(){
        android.support.v4.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new Review()).commit();
    }

    public void initialCard(List<Flashcard> f, TextView text){
        text.setText(f.get(0).getQText());
        int format = formatChoice(f.get(flashcardIndex));
        switch (format){
            case  0:
                multipleChoiceEasy(f.get(flashcardIndex).getQText(), f.get(flashcardIndex).getAText());
                break;
            case  1:
                multipleChoiceHard(f.get(flashcardIndex).getQText(), f.get(flashcardIndex).getAText());
                break;
            case  2:
                normalFlashcard(f.get(flashcardIndex).getQText(), f.get(flashcardIndex).getAText());
                break;
        }
    }

    public int formatChoice(Flashcard f){
        int formatMarker = 0;
        if(f.getScore() > 0){
            formatMarker = 0;
        }
        if(f.getScore() > 50){
            formatMarker = 1;
        }
        if(f.getScore() > 100){
            formatMarker = 2;
        }
        return formatMarker;
    }

    public void multipleChoiceEasy(String question, String answer){
        LinearLayout buttonContainer = view.findViewById(R.id.multipleChoiceButtons);
        TextView questionText = view.findViewById(R.id.card);
        questionText.setText(question);
        Button continueButton = view.findViewById(R.id.continueButton);
        buttonContainer.setVisibility(View.VISIBLE);
        continueButton.setVisibility(GONE);
        List<Button> buttonList = new ArrayList();
        Button answer1 = view.findViewById(R.id.choice1);
        Button answer2 = view.findViewById(R.id.choice2);
        Button answer3 = view.findViewById(R.id.choice3);
        Button answer4 = view.findViewById(R.id.choice4);
        buttonList.add(answer1);
        buttonList.add(answer2);
        buttonList.add(answer3);
        buttonList.add(answer4);

        Random rand = new Random();
        ArrayList<Integer> usedIndex = new ArrayList<>();
        for(int i = 0 ; i <= 3 ; i++){
            int answerPos = rand.nextInt(4);
            while(usedIndex.contains(answerPos)){
                answerPos = rand.nextInt(4);
            }
            usedIndex.add(answerPos);
        }
        String correctAnswer = answer;
        ArrayList<String> answers = populateAnswerListEasy(correctAnswer);
        for(int i = 0 ; i <= 3 ; i++){
            //sets the button texts to a randomly indexed answer
            buttonList.get(i).setText(answers.get(usedIndex.get(i)));
        }
        continueButton.setVisibility(View.GONE);
    }

    public Boolean checkAnswer(String answer, List<Flashcard> fList){
        Boolean result = false;
        if(answer == fList.get(flashcardIndex).getAText()){
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }

    public void multipleChoiceHard(String question, String answer){
        LinearLayout buttonContainer = view.findViewById(R.id.multipleChoiceButtons);
        TextView questionText = view.findViewById(R.id.card);
        questionText.setText(answer);
        Button continueButton = view.findViewById(R.id.continueButton);
        buttonContainer.setVisibility(View.VISIBLE);
        continueButton.setVisibility(GONE);
        List<Button> buttonList = new ArrayList();
        Button answer1 = view.findViewById(R.id.choice1);
        Button answer2 = view.findViewById(R.id.choice2);
        Button answer3 = view.findViewById(R.id.choice3);
        Button answer4 = view.findViewById(R.id.choice4);
        buttonList.add(answer1);
        buttonList.add(answer2);
        buttonList.add(answer3);
        buttonList.add(answer4);

        Random rand = new Random();
        ArrayList<Integer> usedIndex = new ArrayList<>();
        for(int i = 0 ; i <= 3 ; i++){
            int answerPos = rand.nextInt(4);
            while(usedIndex.contains(answerPos)){
                answerPos = rand.nextInt(4);
            }
            usedIndex.add(answerPos);
        }
        String correctAnswer = question;
        ArrayList<String> answers = populateAnswerListHard(correctAnswer);
        for(int i = 0 ; i <= 3 ; i++){
            //sets the button texts to a randomly indexed answer
            buttonList.get(i).setText(answers.get(usedIndex.get(i)));
        }
        continueButton.setVisibility(View.GONE);
    }

    public void normalFlashcard(String question, String answer){
        TextView questionText = view.findViewById(R.id.card);
        questionText.setText(question);
        LinearLayout buttons = view.findViewById(R.id.buttonContainer);
        Button continueButton = view.findViewById(R.id.continueButton);
        continueButton.setVisibility(View.VISIBLE);
        buttons.setVisibility(GONE);

    }

    public ArrayList<String> populateAnswerListEasy(String correctAnswer){
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> flashcardList = fModel.getAllFlashcards();
        Random rand = new Random();
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<String> usedAnswer = new ArrayList<>();

        int flashcardCount = flashcardList.size();

        //if statement for when there are not enough cards to populate dummy answers
        if(flashcardCount < 4){
            //TODO: create a resource of random Japanese words for now
        }
        else{
            for(int i = 0 ; i <= 3 ; i++){
                int index = rand.nextInt(flashcardCount);
                while((flashcardList.get(index).getAText() == correctAnswer) && usedAnswer.contains(flashcardList.get(index))){
                    index = rand.nextInt(flashcardCount);
                }
                Log.d("Test1542", "Not Working");
                answers.add(flashcardList.get(index).getAText());
                usedAnswer.add(flashcardList.get(index).getAText());
            }
        }
        //PROBLEM: the app is not assigning answers properly

        while(!answers.contains(correctAnswer)){
            answers = populateAnswerListEasy(correctAnswer);
        }

        return answers;
    }

    public ArrayList<String> populateAnswerListHard(String correctAnswer){
        Application app = getActivity().getApplication();
        FlashcardViewModel fModel = new FlashcardViewModel(app);
        List<Flashcard> flashcardList = fModel.getAllFlashcards();
        Random rand = new Random();
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<String> usedAnswer = new ArrayList<>();

        int flashcardCount = flashcardList.size();

        //if statement for when there are not enough cards to populate dummy answers
        if(flashcardCount < 4){
            //TODO: create a resource of random Japanese words for now
        }
        else{
            for(int i = 0 ; i <= 3 ; i++){
                int index = rand.nextInt(flashcardCount);
                while((flashcardList.get(index).getQText() == correctAnswer) && usedAnswer.contains(flashcardList.get(index))){
                    index = rand.nextInt(flashcardCount);
                }
                Log.d("Test1542", "Not Working");
                answers.add(flashcardList.get(index).getQText());
                usedAnswer.add(flashcardList.get(index).getAText());
            }
        }
        while(!answers.contains(correctAnswer)){
            answers = populateAnswerListHard(correctAnswer);
        }
        //PROBLEM: the app is not assigning answers properly

        return answers;
    }
}
