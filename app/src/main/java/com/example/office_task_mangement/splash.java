package com.example.office_task_mangement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final FirebaseAuth mauth;
        LottieAnimationView lottieAnimationView = findViewById(R.id.lootie);
        LottieAnimationView lottieAnimationView1 = findViewById(R.id.lootie1);
        mauth = FirebaseAuth.getInstance();
        final ProgressDialog login_dialog = new ProgressDialog(getApplicationContext());
        lottieAnimationView.animate().setDuration(5000).setStartDelay(15);
        lottieAnimationView1.animate().setDuration(5000).setStartDelay(15);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
                finish();
            }
        }, 4000);


    }

    }
