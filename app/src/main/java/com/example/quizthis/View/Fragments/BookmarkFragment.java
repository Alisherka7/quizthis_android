package com.example.quizthis.View.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.quizthis.R;
import com.example.quizthis.View.FlashCardsActivitry;
import com.example.quizthis.View.QuizActivity;
import com.example.quizthis.View.QuizGameModeActivity;

public class BookmarkFragment extends Fragment {

    LinearLayout flashCards, quizMode;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        flashCards = view.findViewById(R.id.flashModeButton);
        quizMode = view.findViewById(R.id.quizModeButton);


        flashCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FlashCardsActivitry.class);
                startActivity(intent);
            }
        });

        quizMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizGameModeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}