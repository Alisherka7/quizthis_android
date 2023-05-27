package com.example.quizthis.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizthis.Model.Authentication.LoginResponse;
import com.example.quizthis.Model.Authentication.LoginDto;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;
import com.example.quizthis.View.Fragments.FragmentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    SharedPreferences sharedPreferences;
    TextView loginTxt, passwordTxt;
    TextView registrationTxt;
    Button submitLogin;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        loginTxt = findViewById(R.id.login);
        passwordTxt = findViewById(R.id.password);
        registrationTxt = findViewById(R.id.registrationButton);
        submitLogin = findViewById(R.id.submitLogin);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnloginClicked();
            }
        });

        // 회원가입
        registrationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenticationActivity.this, AuthorizationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void btnloginClicked(){
        String email = loginTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Login", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else{
            RestApi apiInterface = RetrofitService.getRetrofitInstance(AuthenticationActivity.this).create(RestApi.class);
            Call<LoginResponse> call = apiInterface.login(new LoginDto(email, password));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if(response.body() == null){
                        Toast.makeText(AuthenticationActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(AuthenticationActivity.this, "Logged in successul", Toast.LENGTH_SHORT).show();

                        //when login is success -> get user TOKEN (email,password data) and Save on shared preferences with key TOKEN

                        String TOKEN = response.body().getToken();
                        Log.e(TAG, "onResponse: token->" + TOKEN);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("TOKEN", TOKEN);
                        editor.commit();

                        Intent intent = new Intent(AuthenticationActivity.this, FragmentActivity.class);
                        startActivity(intent);
                    }
                    Log.e(TAG, "onResponse: "+ response.code());
                    Log.e(TAG, "onResponse: "+ response.body());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+ t.getMessage());
                    Toast.makeText(AuthenticationActivity.this, "Incorrect Email or password", Toast.LENGTH_SHORT).show();
                }

            });

        }
    }
}