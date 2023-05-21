package com.example.quizthis.View.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.quizthis.R;
import com.example.quizthis.databinding.ActivityFragmentBinding;

public class FragmentActivity extends AppCompatActivity {
    ActivityFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        binding = ActivityFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setItemIconTintList(null);
        HomeFragment homeFragment = new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        BookmarkFragment bookmarkFragment = new BookmarkFragment();
        QuizsetFragment quizsetFragment = new QuizsetFragment();
        ReplacementFragment(homeFragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    ReplacementFragment(homeFragment);
                    break;
                case R.id.bookmark:
                    ReplacementFragment(bookmarkFragment);
                    break;
                case R.id.sets:
                    ReplacementFragment(quizsetFragment);
                    break;
                case R.id.profile:
                    ReplacementFragment(profileFragment);
                    break;
            }

            return true;
        });
//        logOut = (Button) findViewById(R.id.logOutBtn);
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove("login");
//                editor.apply();
//                Intent intent = new Intent(QuizPageActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    private void ReplacementFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}