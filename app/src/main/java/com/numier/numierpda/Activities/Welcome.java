package com.numier.numierpda.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.numier.numierpda.Fragments.WelcomeFragment;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

public class Welcome extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        // Si el campo configured es true es porque ya ha iniciado sesion y abrira la caja
        if (PreferencesTools.getValueOfPreferences(this, "configured").equals("")) {
            getSupportFragmentManager().beginTransaction().add(R.id.containerWelcome, new WelcomeFragment()).commit();
        } else {
            Intent intent = new Intent(Welcome.this, Main.class);
            startActivity(intent);
            finish();
        }
    }


}
