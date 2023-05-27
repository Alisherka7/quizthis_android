package com.example.quizthis.View;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizthis.Model.Response.QuizsetResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashCardsActivitry extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Button backBtn;
    public static final String MyPREFERENCES = "MyPrefs";
    LinearLayout quizsetLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards_activitry);

        sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        backBtn = (Button)findViewById(R.id.backBtn);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDatas();
    }




    // get datas request
    private void getDatas(){
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(this).create(RestApi.class);
        Call<List<QuizsetResponse>> getQuizsets = apiInterface.getQuizset("Bearer" + TOKEN);
        quizsetLayout = findViewById(R.id.quizsetLayout);
        quizsetLayout.removeAllViews();
        quizsetLayout.setGravity(Gravity.CENTER);


        getQuizsets.enqueue(new Callback<List<QuizsetResponse>>() {
            @Override
            public void onResponse(Call<List<QuizsetResponse>> call, Response<List<QuizsetResponse>> response) {
                if (response.isSuccessful()) {
                    List<QuizsetResponse> quizsets = response.body();

                    for(QuizsetResponse sets : quizsets){

                        System.out.println(sets.getTitle() + "--->>>>>");

                        Integer quizsetId = sets.getQuizsetid();


                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                dpToPx(80));
                        params.gravity = Gravity.CENTER_VERTICAL;
                        params.setMargins(0,0,0,30);

                        LinearLayout parentLayout = new LinearLayout(getApplication());
                        parentLayout.setId(sets.getQuizsetid());
                        parentLayout.setLayoutParams(params);
                        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
                        parentLayout.setGravity(Gravity.CENTER_VERTICAL);
                        parentLayout.setPadding(dpToPx(10), 0, dpToPx(10), 0); // Convert dp to pixels for padding

// Create the progress TextView
                        TextView progressTextView = new TextView(getApplication());
                        progressTextView.setId(R.id.progress);
                        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
                                dpToPx(60), // Convert dp to pixels, you can define this helper method
                                dpToPx(60)  // Convert dp to pixels, you can define this helper method
                        );


                        progressTextView.setLayoutParams(progressParams);
                        progressTextView.setGravity(Gravity.CENTER);
                        progressTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

                        if(sets.getItems() == 0){
                            parentLayout.setBackgroundResource(R.drawable.no_item_block);
                        }else{
                            parentLayout.setBackgroundResource(R.drawable.quizset_block);
                            progressTextView.setText("100%");
                            progressTextView.setBackgroundResource(R.drawable.circle);
                        }

// Create the title TextView
                        TextView titleTextView = new TextView(getApplication());
                        titleTextView.setId(R.id.title);
                        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                dpToPx(60) // Convert dp to pixels, you can define this helper method
                        );
                        titleTextView.setLayoutParams(titleParams);
                        titleTextView.setGravity(Gravity.CENTER);
                        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        titleTextView.setText(sets.getTitle());

                        parentLayout.addView(progressTextView);
                        parentLayout.addView(titleTextView);
                        quizsetLayout.addView(parentLayout);


                        backBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });




                        // Set on click
                        if(sets.getItems() > 0){
                            parentLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplication(), StartFlashCardActivity.class);
                                    intent.putExtra("quizsetId", quizsetId);
                                    intent.putExtra("title", sets.getTitle());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }

            private int dpToPx(int dp) {
                float scale = getResources().getDisplayMetrics().density;
                return (int) (dp * scale + 0.5f);
            }

            @Override
            public void onFailure(Call<List<QuizsetResponse>> call, Throwable t) {
                // Handle network failure
                Log.e(TAG, "Failed to get user data: " + t.getMessage());

            }
        });
    }

}