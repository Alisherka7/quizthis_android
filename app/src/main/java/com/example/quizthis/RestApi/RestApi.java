package com.example.quizthis.RestApi;

import com.example.quizthis.Model.Authentication.LoginResponse;
import com.example.quizthis.Model.LoginDto;
import com.example.quizthis.Model.Response.QuizsetResponse;
import com.example.quizthis.Model.Response.UserDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestApi {

    // Authorization
    @POST("/api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginDto request);


    // Get user datas
    @GET("/user")
    Call<UserDataResponse> getUserData(@Header("Authorization") String TOKEN);

    // Get Quizsets
    @GET("/api/v1/quizset/get")
    Call<List<QuizsetResponse>> getQuizset(@Header("Authorization") String TOKEN);
}
