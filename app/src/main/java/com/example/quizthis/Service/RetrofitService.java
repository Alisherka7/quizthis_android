package com.example.quizthis.Service;


import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static Retrofit retrofit;


    public static Retrofit getRetrofitInstance(Context context){
        if(retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://222.105.43.106:3030")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofit = retrofit.newBuilder().client(client).build();

        }
        return retrofit;
    }

//    a custom Interceptor  adds the Bearer token to the header of all requests:

    private static class AuthInterceptor implements Interceptor {

        public static final String MyPREFERENCES = "MyPrefs";
        SharedPreferences sharedPreferences;

        AuthInterceptor(Context context){
            sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // Get the authorization token from SharedPreferences
            String TOKEN = sharedPreferences.getString("TOKEN", "");


            //Add the token to the request header
            Request authorizedRequest = request.newBuilder()
                    .header("Authorization", "Bearer " + TOKEN)
                    .build();

            return chain.proceed(authorizedRequest);
        }
    }



}