package com.example.quizthis;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.quizthis.Model.Response.UserDataResponse;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;
import com.example.quizthis.View.AuthenticationActivity;
import com.example.quizthis.View.Fragments.FragmentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String TOKEN = sharedPreferences.getString("TOKEN", null);
        checkToken(TOKEN);


        if(TOKEN != null){
            Intent intent = new Intent(MainActivity.this, FragmentActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
            startActivity(intent);
        }

    }

    private void checkToken(String TOKEN){

        RestApi apiInterface = RetrofitService.getRetrofitInstance(MainActivity.this).create(RestApi.class);
        Call<UserDataResponse> userProfileCall = apiInterface.getUserData("Bearer" + TOKEN);
        userProfileCall.enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                if(response.code() == 403){
                    Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    if (response.isSuccessful()) {
                        UserDataResponse userProfileResponse = response.body();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {
                // Handle network failure
                Log.e(TAG, "Failed to get user data: " + t.getMessage());

            }
        });

    }
}