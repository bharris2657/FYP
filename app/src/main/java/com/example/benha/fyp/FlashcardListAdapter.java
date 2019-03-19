package com.example.benha.fyp;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class FlashcardListAdapter extends RecyclerView.Adapter<FlashcardListAdapter.FlashcardViewHolder> implements ItemClickListener{

    class FlashcardViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        private final TextView flashcardQuestion;
        private final TextView flashcardAnswer;
        private final TextView flashcardScore;

        private FlashcardViewHolder(View itemView){
            super(itemView);
/*          flashcardItemView = itemView.findViewById(R.id.questionText1);*/
            flashcardQuestion = itemView.findViewById(R.id.questionText1);
            flashcardAnswer = itemView.findViewById(R.id.answerText1);
            flashcardScore = itemView.findViewById(R.id.score1);
        }

        @Override
        public void onClick(View v){
            int id = v.getId();

        }

    }



    private ItemClickListener onItemClickListener;
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
            position = holder.getAdapterPosition();
            Flashcard current = flashcards.get(position);
            holder.flashcardQuestion.setText(current.getQText());
            holder.flashcardAnswer.setText(current.getAText());
            holder.flashcardScore.setText(""+current.getScore());

        }
        else{
            holder.flashcardQuestion.setText("No Flashcards");
        }
    }

    public void setOnItemClickListener(ItemClickListener clickListener){
        onItemClickListener = clickListener;
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

/*    @Override
    public void onClick(View view){
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        onItemClickListener.onItemClick(view, position);
        Log.d("Test1743", "Hello");

    }*/

    @Override
    public void onItemClick(View view, int position){
        onItemClickListener.onItemClick(view, position);
        Log.d("Test1743", "Hello");
    }
}

