package com.example.benha.fyp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Homepage extends Fragment {

public Homepage(){}


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homepage, container, false);
        Button reviewButton = view.findViewById(R.id.reviewButton);
        final android.support.v4.app.FragmentManager fm = getFragmentManager();

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if(id == R.id.reviewButton) {
                    fm.beginTransaction().replace(R.id.content_frame, new Review()).commit();
                }
            }
        });
        Button learnButton = view.findViewById(R.id.learnButton);
        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if(id == R.id.learnButton) {
                    fm.beginTransaction().replace(R.id.content_frame, new Learn()).commit();
                }
            }
        });
        return view;
        }

/*    @Override
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.reviewButton){
            fm.beginTransaction().replace(R.id.content_frame, new Review()).commit();
        }
        if (id == R.id.learnButton){
            fm.beginTransaction().replace(R.id.content_frame, new Learn()).commit();
        }
    }*/

}
