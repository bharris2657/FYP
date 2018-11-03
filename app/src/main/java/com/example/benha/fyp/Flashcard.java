package com.example.benha.fyp;

import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Flashcard {

    private int questionIndex;
    private int subInfoIndex;
    private int score;
    private ArrayList<String[]> cardList = populateArrayList();


    public Flashcard(int qIndex, int sIndex){
        questionIndex = qIndex;
        subInfoIndex = sIndex;

    }

    public void saveScore(int index, int score){
        //get a list which represents a card
        String[] card = cardList.get(index);
        //make a string builder in order to join the card together
        StringBuilder fullLine = new StringBuilder();
        File file = new File(Environment.getExternalStorageDirectory(),"cardDatabase.txt");
        //put the card into one string called fullLine
        for(int i = 0; i < card.length ; i++){
            fullLine.append(card[i]+";");
        }
        String oldContent = "", newContent = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            //line loads in the first line of the file
            String line = reader.readLine();
            if(index == 0){
                oldContent += oldContent + line + "\n";
            }
            else {
                for (int i = 0; i <= index; i++) {
                    //oldconent adds itself and the first line
                    oldContent = oldContent + line + "\n";
                    //line changes to the next line
                    line = reader.readLine();
                }
            }
            Log.d("test1612", oldContent);

            ArrayList<String> oldContentArray = new ArrayList<String>(Arrays.asList(oldContent.split(System.lineSeparator())));
            //changes element in oldContentArray to new information
            oldContentArray.set(index, fullLine.toString());
            //for loop to write oldContentArray to new string
            for(int i = 0 ; i <= oldContentArray.size()-1 ; i++){
                newContent = newContent + oldContentArray.get(i) + System.lineSeparator();
            }

            //outputs to file
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(String oldDb:oldContentArray){
                out.write(oldDb);
                out.newLine();
            }
            out.close();


        }catch (IOException e){
                Log.d("test1448", e.getMessage());
        }
    }

    public String[] getCard(int i){
        return cardList.get(i);
    }

    public String getQuestion(int i){
        return cardList.get(i)[0];
    }

    public String getAnswer(int i){
        return cardList.get(i)[1];
    }

    public ArrayList<String[]> populateArrayList() {

        ArrayList<String[]> flashCards = new ArrayList<String[]>();
        //loads text database
        File file = new File(Environment.getExternalStorageDirectory(),"cardDatabase.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                //creates arraylist of arrays referenced by 0 0 for question or 0 1 for answer 0 2 for score etc
                String[]lineArray = line.split(";");
                flashCards.add(lineArray);
            }

            br.close();
        }
        catch (IOException e) {

        }

        return flashCards;
    }


}
