package com.numier.numierpda.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.numier.numierpda.Fragments.MainFragment;
import com.numier.numierpda.R;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main, new MainFragment())
                .commit();

    }
}
