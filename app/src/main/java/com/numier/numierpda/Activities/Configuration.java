package com.numier.numierpda.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.numier.numierpda.Controllers.CheckKey;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;


public class Configuration extends AppCompatActivity {

    Button scanQR, connectServer;
    EditText ipServer, serialServer;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuration_step_1);

        progressDialog = new ProgressDialog(this);

        ipServer = (EditText) findViewById(R.id.ipServer);
        serialServer = (EditText) findViewById(R.id.serialServer);

        scanQR = (Button) findViewById(R.id.scanQR);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(Configuration.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt(getResources().getString(R.string.prompt_scan_qr));
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        connectServer = (Button) findViewById(R.id.connectServer);
        connectServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ipServer.getText().toString();
                String clave = serialServer.getText().toString();

                // Si no incluye el puerto se lo pongo
                if (!ip.contains(":")) {
                    ip += ":81";
                }

                if (!ip.equals("") && !clave.equals("")) {
                    saveServer(ip, clave);
                } else {
                    Snackbar.make(findViewById(R.id.relativeConfiguration), getResources().getText(R.string.complete_input), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    public void saveServer(String ip, String clave){
        PreferencesTools.savePreferences(Configuration.this, "base_url", "http://"+ip+"/controllers/");
        PreferencesTools.savePreferences(Configuration.this, "url", ip);
        PreferencesTools.savePreferences(Configuration.this, "key", clave);
        new CheckKey(Configuration.this).execute();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null) {
            if(result.getContents() == null) {
                Snackbar.make(findViewById(R.id.relativeConfiguration), getResources().getString(R.string.scan_qr_cancelled), Snackbar.LENGTH_LONG).show();
            } else {
                // DATA 0 = Direccion ip Server
                // DATA 1 = Clave Server
                String[] data = result.getContents().split("#");
                saveServer(data[0], data[1]);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }


}


