package com.example.quizthis.View;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizthis.Model.Request.QuizRequest;
import com.example.quizthis.Model.Response.NewQuizResponse;
import com.example.quizthis.Model.Response.NewQuizsetResponse;
import com.example.quizthis.Model.Response.QuizResponse;
import com.example.quizthis.Model.Response.QuizsetRequest;
import com.example.quizthis.Model.Response.QuizsetResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    TextView quizsetTitleTxt;
    LinearLayout addQuizButton;
    LinearLayout quizListLayout;
    Integer quizsetid = -1;
    String title = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // get token
        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        quizsetTitleTxt = findViewById(R.id.quizsetTitleTxt);
        addQuizButton = findViewById(R.id.addQuizButton);
        quizListLayout = findViewById(R.id.quizListLayout);
        quizsetid = getIntent().getIntExtra("quizsetid", -1);
        title = getIntent().getStringExtra("title");

        quizsetTitleTxt.setText(title);
        // request
        getDatas();

        addQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuizDialog();
            }
        });

    }


    private void addQuizDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_quiz_dialog);

        EditText question = dialog.findViewById(R.id.question);
        EditText answer = dialog.findViewById(R.id.answer);

        final Button addQuiz = dialog.findViewById(R.id.addButton);
        final Button closeQuiz = dialog.findViewById(R.id.closeButton);

        addQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getText().toString().isEmpty()){
                    Toast.makeText(QuizActivity.this, "Please Enter Question", Toast.LENGTH_SHORT).show();
                }else if(answer.getText().toString().isEmpty()){
                    Toast.makeText(QuizActivity.this, "Please Enter Answer", Toast.LENGTH_SHORT).show();
                }else{

                    // Post request -> Add new quiz
                    addQuizRequest(question.getText().toString(), answer.getText().toString(), dialog);
                }
            }
        });

        closeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.65);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    private void getDatas(){
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(this).create(RestApi.class);
        if(quizsetid != -1){
            Call<List<QuizResponse>> getQuizsets = apiInterface.getQuiz("Bearer" + TOKEN, quizsetid);
            quizListLayout.removeAllViews();

            getQuizsets.enqueue(new Callback<List<QuizResponse>>() {
                @Override
                public void onResponse(Call<List<QuizResponse>> call, Response<List<QuizResponse>> response) {
                    if (response.isSuccessful()) {
                        List<QuizResponse> quiz = response.body();
                        for(QuizResponse qr : quiz){
                            // Create the parent LinearLayout
                            LinearLayout parentLayout = new LinearLayout(getApplication());
                            LinearLayout.LayoutParams parentLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    dpToPx(200) // Convert dp to pixels, you can define this helper method
                            );


                            parentLayoutParams.setMargins(0, 0, 0, dpToPx(10)); // Convert dp to pixels, you can define this helper method
                            parentLayout.setLayoutParams(parentLayoutParams);
                            parentLayout.setBackgroundResource(R.drawable.block_quiz_green);
                            parentLayout.setPadding(dpToPx(20), dpToPx(20), dpToPx(20), 0);
                            parentLayout.setOrientation(LinearLayout.VERTICAL);

// Create the question EditText
                            EditText questionEditText = new EditText(getApplication());
                            LinearLayout.LayoutParams questionEditTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    dpToPx(50) // Convert dp to pixels, you can define this helper method
                            );
                            questionEditTextParams.setMargins(0, 0, 0, dpToPx(15)); // Convert dp to pixels, you can define this helper method
                            questionEditText.setLayoutParams(questionEditTextParams);
                            questionEditText.setBackgroundResource(R.drawable.input_border);
                            questionEditText.setHint("Question");
                            questionEditText.setText(qr.getQuestion());
                            questionEditText.setPadding(dpToPx(10), 0, 0, 0);

// Create the answer EditText
                            EditText answerEditText = new EditText(getApplication());
                            LinearLayout.LayoutParams answerEditTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    dpToPx(50) // Convert dp to pixels, you can define this helper method
                            );
                            answerEditTextParams.setMargins(0, 0, 0, dpToPx(15)); // Convert dp to pixels, you can define this helper method
                            answerEditText.setLayoutParams(answerEditTextParams);
                            answerEditText.setBackgroundResource(R.drawable.input_border);
                            answerEditText.setHint("Answer");
                            answerEditText.setText(qr.getAnswer());
                            answerEditText.setPadding(dpToPx(10), 0, 0, 0);

// Create the editButton
                            Button editButton = new Button(getApplication());
                            LinearLayout.LayoutParams editButtonParams = new LinearLayout.LayoutParams(
                                    dpToPx(30), // Convert dp to pixels, you can define this helper method
                                    dpToPx(30)  // Convert dp to pixels, you can define this helper method
                            );
                            editButtonParams.gravity = Gravity.RIGHT;
                            editButton.setLayoutParams(editButtonParams);
                            editButton.setBackgroundResource(R.drawable.trash);
                            editButton.setId(R.id.editButton); // Set the ID for referencing

                            // delete item
                            editButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deleteRequest(qr.getQuizid());
                                }
                            });

// Add the views to the parent LinearLayout
                            parentLayout.addView(questionEditText);
                            parentLayout.addView(answerEditText);
                            parentLayout.addView(editButton);

                            quizListLayout.addView(parentLayout);

                        }
                    }
                }

                @Override
                public void onFailure(Call<List<QuizResponse>> call, Throwable t) {
                    Log.e(TAG, "Failed to get quiz data: " + t.getMessage());
                }
            });
        }

    }

    private void deleteRequest(Integer quizId){

    }


    private void addQuizRequest(String question, String answer, Dialog dialog){
        QuizRequest quizRequest = new QuizRequest(quizsetid,question, answer);

        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(QuizActivity.this).create(RestApi.class);
        Call<NewQuizResponse> addQuizset = apiInterface.addQuiz("Bearer" + TOKEN, quizRequest);
        addQuizset.enqueue(new Callback<NewQuizResponse>() {
            @Override
            public void onResponse(Call<NewQuizResponse> call, Response<NewQuizResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(QuizActivity.this, "Quiz Inserted Successfully!", Toast.LENGTH_SHORT).show();
                    getDatas();
                    dialog.cancel();
                }
            }
            @Override
            public void onFailure(Call<NewQuizResponse> call, Throwable t) {
                // Handle network failure
                Log.e(TAG, "Failed to add quizset: " + t.getMessage());

            }
        });
    }


    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}