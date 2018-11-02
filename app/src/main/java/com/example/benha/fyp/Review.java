package com.example.benha.fyp;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Review extends Fragment implements View.OnClickListener {


    private android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();

    private ArrayList<String> flashCards = new ArrayList<String>();

    public Review(){}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View thisView = inflater.inflate(R.layout.activity_review, container, false);
        Button startReview = (Button) thisView.findViewById(R.id.startReview);
        startReview.setOnClickListener(this);

        return thisView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    }

    @Override
    public void onClick(View v){
        Button startReview = (Button) v.findViewById(R.id.startReview);
        startReview.setVisibility(View.GONE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ReviewBehaviour()).commit();
    }



}
