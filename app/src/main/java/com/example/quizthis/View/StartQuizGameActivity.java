package com.example.quizthis.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizthis.Model.Response.QuizGameResponse;
import com.example.quizthis.Model.Response.QuizResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartQuizGameActivity extends AppCompatActivity {

    String title = "";
    Integer quizsetId = -1;

    Button backBtn, nextButton;
    TextView question, firstv, secondv, thirdv, fourthv, currentPosition, titleTxt;
    List<QuizGameResponse> quiz = new ArrayList<>();
    SharedPreferences sharedPreferences;
    Integer pos = 0;
    Integer rightAnswer = 0;
    Integer selectedIndex = -1;
    public static final String MyPREFERENCES = "MyPrefs";

    int totalQuestions;
    int selectedAnswers = 0;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz_game);

        pos = 0;

        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        quizsetId = getIntent().getIntExtra("quizsetId", -1);
        title = getIntent().getStringExtra("title");

        titleTxt = findViewById(R.id.titleTxt);

        titleTxt.setText(title);

        question = findViewById(R.id.question);
        firstv = findViewById(R.id.firstv);
        secondv = findViewById(R.id.secondv);
        thirdv = findViewById(R.id.thirdv);
        fourthv = findViewById(R.id.fourthv);
        currentPosition = findViewById(R.id.currentPosition);

        backBtn = findViewById(R.id.backBtn);
        nextButton = findViewById(R.id.nextButton);

        getDatas();



        firstv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIndex == -1){
                    firstv.setBackgroundResource(R.drawable.block_quiz_green);
                    selectedIndex = 0;
                }else if(selectedIndex == 0){
                    selectedIndex = -1;
                    firstv.setBackgroundResource(R.drawable.input_border);
                } else{
                    selectedIndex = 0;
                    firstv.setBackgroundResource(R.drawable.block_quiz_green);
                    secondv.setBackgroundResource(R.drawable.input_border);
                    thirdv.setBackgroundResource(R.drawable.input_border);
                    fourthv.setBackgroundResource(R.drawable.input_border);
                }
            }
        });

        secondv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIndex == -1){
                    secondv.setBackgroundResource(R.drawable.block_quiz_green);
                    selectedIndex = 1;
                }else if(selectedIndex == 1){
                    selectedIndex = -1;
                    secondv.setBackgroundResource(R.drawable.input_border);
                } else{
                    selectedIndex = 1;
                    secondv.setBackgroundResource(R.drawable.block_quiz_green);
                    firstv.setBackgroundResource(R.drawable.input_border);
                    thirdv.setBackgroundResource(R.drawable.input_border);
                    fourthv.setBackgroundResource(R.drawable.input_border);
                }
            }
        });

        thirdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIndex == -1){
                    thirdv.setBackgroundResource(R.drawable.block_quiz_green);
                    selectedIndex = 2;
                }else if(selectedIndex == 2){
                    selectedIndex = -1;
                    thirdv.setBackgroundResource(R.drawable.input_border);
                } else{
                    selectedIndex = 2;
                    thirdv.setBackgroundResource(R.drawable.block_quiz_green);
                    secondv.setBackgroundResource(R.drawable.input_border);
                    firstv.setBackgroundResource(R.drawable.input_border);
                    fourthv.setBackgroundResource(R.drawable.input_border);
                }
            }
        });

        fourthv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIndex == -1){
                    fourthv.setBackgroundResource(R.drawable.block_quiz_green);
                    selectedIndex = 3;
                }else if(selectedIndex == 3){
                    selectedIndex = -1;
                    fourthv.setBackgroundResource(R.drawable.input_border);
                } else{
                    selectedIndex = 3;
                    fourthv.setBackgroundResource(R.drawable.block_quiz_green);
                    secondv.setBackgroundResource(R.drawable.input_border);
                    thirdv.setBackgroundResource(R.drawable.input_border);
                    firstv.setBackgroundResource(R.drawable.input_border);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex != -1) {
                    checkAnswer();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setQuestion(int position) {
        QuizGameResponse currentQuestion = quiz.get(position);

        question.setText(currentQuestion.getQuestion());
        firstv.setText(currentQuestion.getFirstVersion());
        secondv.setText(currentQuestion.getSecondVersion());
        thirdv.setText(currentQuestion.getThirdVersion());
        fourthv.setText(currentQuestion.getFourthVersion());

        // Reset selected answer and background color
        selectedIndex = -1;
        firstv.setBackgroundResource(R.drawable.input_border);
        secondv.setBackgroundResource(R.drawable.input_border);
        thirdv.setBackgroundResource(R.drawable.input_border);
        fourthv.setBackgroundResource(R.drawable.input_border);

        currentPosition.setText((position + 1) + "/" + totalQuestions);
    }

    private void checkAnswer() {
        QuizGameResponse currentQuestion = quiz.get(pos);

        if (currentQuestion.getAnswer().equals(getSelectedAnswer())) {
            correctAnswers++;
        }

        pos++;
        selectedAnswers++;

        if (pos < quiz.size()) {
            // Display the next question
            setQuestion(pos);
        } else {
            // Move to the result activity
            Intent intent = new Intent(getApplicationContext(), QuizResultActivity.class);
            intent.putExtra("rightAnswers", correctAnswers);
            intent.putExtra("totalQuestions", totalQuestions);
            startActivity(intent);
            finish();
        }
    }

    private String getSelectedAnswer() {
        switch (selectedIndex) {
            case 0:
                return firstv.getText().toString();
            case 1:
                return secondv.getText().toString();
            case 2:
                return thirdv.getText().toString();
            case 3:
                return fourthv.getText().toString();
            default:
                return "";
        }
    }

    private void getDatas(){
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(this).create(RestApi.class);
        if(quizsetId != -1){
            Call<List<QuizGameResponse>> getQuizsets = apiInterface.quizGameMode("Bearer" + TOKEN, quizsetId);

            getQuizsets.enqueue(new Callback<List<QuizGameResponse>>() {
                @Override
                public void onResponse(Call<List<QuizGameResponse>> call, Response<List<QuizGameResponse>> response) {
                    if (response.isSuccessful()) {
                        quiz = response.body();
                        Toast.makeText(getApplication(), "quizsize ->" + quiz.size(), Toast.LENGTH_SHORT).show();
                        // set Item
                        question.setText(quiz.get(pos).getQuestion());
                        firstv.setText(quiz.get(pos).getFirstVersion());
                        secondv.setText(quiz.get(pos).getSecondVersion());
                        thirdv.setText(quiz.get(pos).getThirdVersion());
                        fourthv.setText(quiz.get(pos).getFourthVersion());
                        System.out.println("Question ------------------" + question.getText());
                        totalQuestions = quiz.size();

                        currentPosition.setText((pos+1)+"/"+quiz.size());
                    }
                }

                @Override
                public void onFailure(Call<List<QuizGameResponse>> call, Throwable t) {

                }
            });
        }
    }
}