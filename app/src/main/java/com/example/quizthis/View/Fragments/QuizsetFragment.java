package com.example.quizthis.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizthis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizsetFragment#newInstance} factory method to
 * create an instance of this fragment.
 * todo Implement new {@link BookmarkFragment}
 */
public class QuizsetFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("Quizset fragment");
        return inflater.inflate(R.layout.fragment_quizset, container, false);
    }
}