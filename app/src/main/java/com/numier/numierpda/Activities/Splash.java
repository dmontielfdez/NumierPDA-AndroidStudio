package com.numier.numierpda.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.numier.numierpda.Fragments.SplashFragment;
import com.numier.numierpda.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_splash, new SplashFragment())
                .commit();
    }
}
