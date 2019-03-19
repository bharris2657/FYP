package com.example.benha.fyp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageCards extends Fragment{

    private FlashcardViewModel fModel;

    public static final int NEW_FLASHCARD_FRAGMENT_REQUEST_CODE = 1;

    public ManageCards() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_cards, container, false);

        FloatingActionButton newCard = view.findViewById(R.id.newCardButton);
        newCard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), NewFlashcard.class);
                startActivityForResult(intent, NEW_FLASHCARD_FRAGMENT_REQUEST_CODE);
            }
        });

        // gets recyclerview and assigns it to the recycler adapter
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final FlashcardListAdapter adapter = new FlashcardListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        fModel = ViewModelProviders.of(getActivity()).get(FlashcardViewModel.class);
        //creates an observer to view the livedata list for changes
        fModel.getLiveCards().observe(getActivity(), new Observer<List<Flashcard>>() {
            @Override
            public void onChanged(@Nullable final List<Flashcard> flashcards) {
                adapter.setFlashcards(flashcards);
            }
        });

        FloatingActionButton deleteAllButton = view.findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fModel.deleteAll();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        //overrides method onactivityresult to collect data from startActivityforresult and insert into db using that data
        if(requestCode == NEW_FLASHCARD_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK){
            Flashcard flashcard = new Flashcard(data.getStringExtra(NewFlashcard.QUESTION_TEXT),
                    data.getStringExtra(NewFlashcard.ANSWER_TEXT));
            fModel.insert(flashcard);
        }
        else{
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    R.string.emptynotsaved,
                    Toast.LENGTH_LONG).show();

        }
    }


}
