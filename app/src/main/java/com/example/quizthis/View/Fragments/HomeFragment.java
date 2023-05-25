package com.example.quizthis.View.Fragments;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizthis.Model.Response.NewQuizsetResponse;
import com.example.quizthis.Model.Response.QuizsetRequest;
import com.example.quizthis.Model.Response.QuizsetResponse;
import com.example.quizthis.Model.Response.UserDataResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;
import com.example.quizthis.View.AuthenticationActivity;
import com.example.quizthis.View.QuizActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomeFragment extends Fragment {
    FragmentManager fragmentManager;

    TextView title, progress;
    LinearLayout addQuizset;
    private View view;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    LinearLayout quizsetLayout;

    public HomeFragment() {
    }

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
        addQuizset = view.findViewById(R.id.addQuizset);


        getDatas(view);

        addQuizset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddQuizsetDialog();

            }
        });
        return view;
    }

    private void openAddQuizsetDialog(){
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_quizset_layout);

        EditText quizsetname = dialog.findViewById(R.id.quizset);
        EditText description = dialog.findViewById(R.id.description);

        final Button addQuiz = dialog.findViewById(R.id.addButton);
        final Button closeQuiz = dialog.findViewById(R.id.closeButton);

        addQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quizsetname.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Enter Quizset", Toast.LENGTH_SHORT).show();
                }else if(description.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();
                }else{
                    addQuizsetRequest(quizsetname.getText().toString(), description.getText().toString(), dialog);
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

    private void addQuizsetRequest(String quizsetname, String description, Dialog dialog){
        QuizsetRequest quizsetRequest = new QuizsetRequest(quizsetname, description);

        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(getContext()).create(RestApi.class);
        Call<NewQuizsetResponse> addQuizset = apiInterface.addQuizset("Bearer" + TOKEN, quizsetRequest);
        addQuizset.enqueue(new Callback<NewQuizsetResponse>() {
            @Override
            public void onResponse(Call<NewQuizsetResponse> call, Response<NewQuizsetResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Quizset Inserted Successfully!", Toast.LENGTH_SHORT).show();
                    getDatas(view);
                    dialog.cancel();
                }
            }
            @Override
            public void onFailure(Call<NewQuizsetResponse> call, Throwable t) {
                // Handle network failure
                Log.e(TAG, "Failed to add quizset: " + t.getMessage());

            }
        });
    }


    private void getDatas(View view){
        String TOKEN = sharedPreferences.getString("TOKEN", null);
        RestApi apiInterface = RetrofitService.getRetrofitInstance(getContext()).create(RestApi.class);
        Call<List<QuizsetResponse>> getQuizsets = apiInterface.getQuizset("Bearer" + TOKEN);
        quizsetLayout = view.findViewById(R.id.quizsetLayout);
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
                                Intent intent = new Intent(getActivity(), QuizActivity.class);
                                intent.putExtra("quizsetid", quizsetId);
                                intent.putExtra("title", sets.getTitle());
                                startActivity(intent);
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