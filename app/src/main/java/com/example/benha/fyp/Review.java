package com.example.benha.fyp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Review extends Fragment implements View.OnClickListener {

    public Review(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.activity_review, container, false);
        Button startReview = thisView.findViewById(R.id.startReview);
        startReview.setOnClickListener(this);

        return thisView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    }

    @Override
    public void onClick(View v){
        Button startReview = v.findViewById(R.id.startReview);
        startReview.setVisibility(View.GONE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ReviewBehaviour()).commit();
    }

}
