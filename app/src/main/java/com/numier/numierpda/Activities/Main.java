package com.numier.numierpda.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.numier.numierpda.Controllers.Init;
import com.numier.numierpda.Fragments.MainFragment;
import com.numier.numierpda.R;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Si el extra es EXIT es para terminar la app
        if (getIntent().getBooleanExtra("EXIT", false)) {
            Intent i = new Intent(Main.this, Welcome.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        // Si es RELOAD es para recargar la sesion
        } else if(getIntent().getBooleanExtra("RELOAD", false)){
            new Init(Main.this).execute();
        } else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new MainFragment())
                    .commit();
        }


    }

    @Override
    public void onBackPressed() {

    }
}
