package com.example.quizthis.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizthis.R;

public class QuizResultActivity extends AppCompatActivity {

    Integer rightAnswer = 0;
    Integer questions = 0;
    String title = "";
    TextView rightAns, rightPercentage, titleTxt;
    Button goHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        rightAnswer = getIntent().getIntExtra("rightAnswers", 0);
        questions = getIntent().getIntExtra("totalQuestions", 0);
        title = getIntent().getStringExtra("title");


        goHome = findViewById(R.id.goHome);
        titleTxt = findViewById(R.id.titleTxt);
        rightAns = findViewById(R.id.rightAns);
        rightPercentage = findViewById(R.id.rightPercentage);


        titleTxt.setText(title);
        rightAns.setText(rightAnswer + "/"+questions);

        double percentage = (double) rightAnswer / questions * 100.0;

        String formattedPercentage = String.format("%.2f%%", percentage);


        if(rightAnswer > 0){
            rightPercentage.setText(formattedPercentage);
        }else{
            rightPercentage.setText("0%");
        }

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}