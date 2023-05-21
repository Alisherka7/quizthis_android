package com.example.quizthis.View.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizthis.Model.Response.UserDataResponse;
import com.example.quizthis.R;
import com.example.quizthis.RestApi.RestApi;
import com.example.quizthis.Service.RetrofitService;
import com.example.quizthis.View.AuthenticationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs";
    Button logOutBtn, editBtn;
    TextView quizsetsTxt, averageTxt, ratingTxt, yourName;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logOutBtn = (Button) view.findViewById(R.id.logOutButton);
        quizsetsTxt = view.findViewById(R.id.quizsetTxt);
        averageTxt = view.findViewById(R.id.averageTxt);
        ratingTxt = view.findViewById(R.id.ratingTxt);
        yourName = view.findViewById(R.id.yourName);
        getDatas();
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Log out clicked");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("TOKEN");
                editor.commit();

                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getDatas(){
        String TOKEN = sharedPreferences.getString("TOKEN", null);

        RestApi apiInterface = RetrofitService.getRetrofitInstance(getContext()).create(RestApi.class);
        Call<UserDataResponse> userProfileCall = apiInterface.getUserData("Bearer" + TOKEN);
        userProfileCall.enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                if (response.isSuccessful()) {
                    UserDataResponse userProfileResponse = response.body();
                    // Handle successful user profile response

                    //user name
                    yourName.setText(userProfileResponse.getUsername());

//
//                    //user Image
//                    if (userProfileResponse.getImageResponse() != null && userProfileResponse.getImageResponse().getImageByte() != null) {
//                        byte[] image = Base64.decode(userProfileResponse.getImageResponse().getImageByte(), Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//                        userImage.setImageBitmap(bitmap);
//                    }



//                    System.out.println(userProfileResponse.getAverageRank());


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