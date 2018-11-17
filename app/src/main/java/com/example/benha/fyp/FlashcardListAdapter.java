package com.example.benha.fyp;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FlashcardListAdapter extends RecyclerView.Adapter<FlashcardListAdapter.FlashcardViewHolder>{

    class FlashcardViewHolder extends RecyclerView.ViewHolder{

        private final TextView flashcardItemView;

        private FlashcardViewHolder(View itemView){
            super(itemView);
            flashcardItemView = itemView.findViewById(R.id.textView);
        }

    }

    private final LayoutInflater mInflater;
    private List<Flashcard> flashcards;

    FlashcardListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public FlashcardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new FlashcardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlashcardViewHolder holder, int position){
        if(flashcards != null){
            Flashcard current = flashcards.get(position);
            holder.flashcardItemView.setText("Q: " + current.getQText() + "   A: " + current.getAText()+"    S:"
            +current.getScore());

        }
        else{
            holder.flashcardItemView.setText("No Flashcards");
        }
    }

    void setFlashcards(List<Flashcard> cards){
        flashcards = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(flashcards != null)
            return flashcards.size();
        else return 0;
    }
}

