package com.example.quizthis.View.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.quizthis.Model.Response.QuizsetResponse;
import com.example.quizthis.Model.Response.UserDataResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomeFragment extends Fragment {

    TextView title, progress;
    private View view;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    LinearLayout quizsetLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sharedPreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        title = view.findViewById(R.id.title);
        progress = view.findViewById(R.id.progress);
        getDatas(view);



        return view;
    }


    private void getDatas(View view){
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(getContext()).create(RestApi.class);
        Call<List<QuizsetResponse>> getQuizsets = apiInterface.getQuizset("Bearer" + TOKEN);
        quizsetLayout = view.findViewById(R.id.quizsetLayout);
        quizsetLayout.setGravity(Gravity.CENTER);

        getQuizsets.enqueue(new Callback<List<QuizsetResponse>>() {
            @Override
            public void onResponse(Call<List<QuizsetResponse>> call, Response<List<QuizsetResponse>> response) {
                if (response.isSuccessful()) {
                    List<QuizsetResponse> quizsets = response.body();

                    for(QuizsetResponse sets : quizsets){

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                dpToPx(80));
                        params.gravity = Gravity.CENTER_VERTICAL;
                        params.setMargins(0,0,0,30);

                        LinearLayout parentLayout = new LinearLayout(getActivity());
                        parentLayout.setId(sets.getQuizsetid());
                        parentLayout.setLayoutParams(params);
                        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
                        parentLayout.setGravity(Gravity.CENTER_VERTICAL);
                        parentLayout.setPadding(dpToPx(10), 0, dpToPx(10), 0); // Convert dp to pixels for padding
                        parentLayout.setBackgroundResource(R.drawable.quizset_block);

// Create the progress TextView
                        TextView progressTextView = new TextView(getActivity());
                        progressTextView.setId(R.id.progress);
                        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
                                dpToPx(60), // Convert dp to pixels, you can define this helper method
                                dpToPx(60)  // Convert dp to pixels, you can define this helper method
                        );


                        progressTextView.setLayoutParams(progressParams);
                        progressTextView.setGravity(Gravity.CENTER);
                        progressTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        progressTextView.setText("100%");
                        progressTextView.setBackgroundResource(R.drawable.circle);

// Create the title TextView
                        TextView titleTextView = new TextView(getActivity());
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




                        // Set on click
                        parentLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                System.out.println(parentLayout.getId());
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuizsetResponse>> call, Throwable t) {
                // Handle network failure
                Log.e(TAG, "Failed to get user data: " + t.getMessage());

            }
        });
    }

    private int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}