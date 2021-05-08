package com.example.office_task_mangement;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity
{
    float v=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FirebaseAuth mauth;
        mauth = FirebaseAuth.getInstance();
        LottieAnimationView lottieAnimationView = findViewById(R.id.loot);
        lottieAnimationView.animate().translationX(0).setDuration(100000).setStartDelay(5);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("SignUp"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        adapter adapter = new adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
