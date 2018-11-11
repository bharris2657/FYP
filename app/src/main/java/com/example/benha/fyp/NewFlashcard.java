package com.example.benha.fyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.app.Activity.RESULT_CANCELED;

public class NewFlashcard extends AppCompatActivity {

    public static final String QUESTION_TEXT = "QUESTION_TEXT";
    public static final String ANSWER_TEXT = "ANSWER_TEXT";

    private EditText editQuestion, editAnswer;

    public NewFlashcard() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flashcard);
        // Inflate the layout for this fragment
        editQuestion = findViewById(R.id.questionText);
        editAnswer = findViewById(R.id.answerText);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(editQuestion.getText()) || TextUtils.isEmpty(editAnswer.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else{
                    String question = editQuestion.getText().toString();
                    String answer = editAnswer.getText().toString();
                    replyIntent.putExtra(QUESTION_TEXT, question);
                    replyIntent.putExtra(ANSWER_TEXT, answer);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
