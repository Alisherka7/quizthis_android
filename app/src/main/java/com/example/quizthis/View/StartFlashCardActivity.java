package com.example.quizthis.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizthis.Model.Response.QuizResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartFlashCardActivity extends AppCompatActivity {

    String title = "";
    Integer quizsetId;

    Button backBtn, nextItem, backItem, shuffleItem;
    TextView frontText, backText, currentPosition;
    List<QuizResponse> quiz = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Integer pos = 0;
    public static final String MyPREFERENCES = "MyPrefs";

    private CardView cardFront;
    private CardView cardBack;
    private AnimatorSet flipAnimation;

    private boolean isFrontVisible = true;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_flash_card);
        pos = 0;


        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        quizsetId = getIntent().getIntExtra("quizsetId", -1);
        title = getIntent().getStringExtra("title");

        getDatas();


        // Find the CardView elements
        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);

        //current position
        currentPosition = findViewById(R.id.currentPosition);


        frontText = findViewById(R.id.frontText);
        backText = findViewById(R.id.backText);

        // buttons
        nextItem = findViewById(R.id.nextItem);
        shuffleItem = findViewById(R.id.shuffleItem);
        backItem = findViewById(R.id.backItem);
        backBtn = findViewById(R.id.backBtn);


        // Back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // next Item
        nextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos < quiz.size()-1){
                    // set Item
                    pos++;
                    frontText.setText(quiz.get(pos).getQuestion());
                    backText.setText(quiz.get(pos).getAnswer());
                    currentPosition.setText(pos+1+"/"+quiz.size());
                }else{
                    pos = 0;
                    frontText.setText(quiz.get(pos).getQuestion());
                    backText.setText(quiz.get(pos).getAnswer());
                    currentPosition.setText((pos+1)+"/"+quiz.size());
                }
            }
        });

        //shuffle items
        shuffleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = 0;
                Collections.shuffle(quiz);
                frontText.setText(quiz.get(pos).getQuestion());
                backText.setText(quiz.get(pos).getAnswer());
                currentPosition.setText((pos+1)+"/"+quiz.size());
            }
        });

        // prev Item
        backItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos > 0){
                    pos--;
                    // set Item
                    frontText.setText(quiz.get(pos).getQuestion());
                    backText.setText(quiz.get(pos).getAnswer());
                    currentPosition.setText((pos+1)+"/"+quiz.size());

                }else{
                    pos = 0;
                    currentPosition.setText((pos+1)+"/"+quiz.size());
                }
            }
        });

        // Load the flip animation
        flipAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.drawable.flip);

        // Set a click listener on the flashcard
        FrameLayout flashcardContainer = findViewById(R.id.flashcardContainer);
        flashcardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipFlashcard();
            }
        });
    }


    private void getDatas(){
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(this).create(RestApi.class);
        if(quizsetId != -1){
            Call<List<QuizResponse>> getQuizsets = apiInterface.getQuiz("Bearer" + TOKEN, quizsetId);

            getQuizsets.enqueue(new Callback<List<QuizResponse>>() {
                @Override
                public void onResponse(Call<List<QuizResponse>> call, Response<List<QuizResponse>> response) {
                    if (response.isSuccessful()) {
                        quiz = response.body();
                        // set Item
                        frontText.setText(quiz.get(pos).getQuestion());
                        backText.setText(quiz.get(pos).getAnswer());

                        currentPosition.setText((pos+1)+"/"+quiz.size());
                    }
                }

                @Override
                public void onFailure(Call<List<QuizResponse>> call, Throwable t) {

                }
            });
        }
    }


    private void flipFlashcard() {
        if (isFrontVisible) {
            flipAnimation.setTarget(cardFront);
            flipAnimation.start();
            cardBack.setVisibility(View.VISIBLE);
            cardFront.setVisibility(View.GONE);
            isFrontVisible = false;
        } else {
            flipAnimation.setTarget(cardBack);
            flipAnimation.start();
            cardFront.setVisibility(View.VISIBLE);
            cardBack.setVisibility(View.GONE);
            isFrontVisible = true;
        }
    }
}