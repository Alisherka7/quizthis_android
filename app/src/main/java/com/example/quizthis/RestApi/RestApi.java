package com.example.quizthis.RestApi;

import com.example.quizthis.Model.Authentication.LoginResponse;
import com.example.quizthis.Model.LoginDto;
import com.example.quizthis.Model.Request.QuizRequest;
import com.example.quizthis.Model.Response.ItemRemoveResponse;
import com.example.quizthis.Model.Response.NewQuizResponse;
import com.example.quizthis.Model.Response.QuizResponse;
import com.example.quizthis.Model.Response.QuizsetRequest;
import com.example.quizthis.Model.Response.QuizsetResponse;
import com.example.quizthis.Model.Response.UserDataResponse;
import com.example.quizthis.Model.Response.NewQuizsetResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    // create Quizsets
    @POST("/api/v1/quizset/create")
    Call<NewQuizsetResponse> addQuizset(@Header("Authorization") String TOKEN, @Body QuizsetRequest quizsetRequest);

    // Delete quizset
    @DELETE("/api/v1/quizset/delete/{quizsetid}")
    Call<ItemRemoveResponse> deleteQuizset(@Header("Authorization") String TOKEN, @Path("quizsetid") Integer quizsetid);


    // Get Quiz
    @GET("/api/v1/quiz/{quizsetid}/get")
    Call<List<QuizResponse>> getQuiz(@Header("Authorization") String TOKEN, @Path("quizsetid") Integer quizsetid);

    // Add quiz
    @POST("/api/v1/quiz/create")
    Call<NewQuizResponse> addQuiz(@Header("Authorization") String TOKEN, @Body QuizRequest quizRequest);

    // Delete quiz
    @DELETE("/api/v1/quiz/delete/{quizid}")
    Call<ItemRemoveResponse> deleteQuiz(@Header("Authorization") String TOKEN, @Path("quizid") Integer quizid);
}
