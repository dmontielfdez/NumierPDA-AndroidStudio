package com.numier.numierpda.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.numier.numierpda.Fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
