package com.example.benha.fyp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Learn extends Fragment implements View.OnClickListener {


    public Learn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_learning, container, false);
        Button startLearning = view.findViewById(R.id.learnStart);
        startLearning.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new LearnBehaviour()).commit();
    }

}
