package com.numier.numierpda.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.numier.numierpda.Activities.Splash;
import com.numier.numierpda.DB.ConnectionsCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.Models.Connection;
import com.numier.numierpda.Models.Worker;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.OkHttpTools;
import com.numier.numierpda.Tools.PreferencesTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.numier.numierpda.Controllers.NumierApi.__WORKER_ID;
import static com.numier.numierpda.Controllers.NumierApi.__WORKER_NAME;
import static com.numier.numierpda.Controllers.NumierApi.__WORKER_PASS;


public class GetWorkers extends AsyncTask<Void, Void, String> {

    Activity activity;
    String key, url;
    ProgressDialog progressDialog;

    public GetWorkers(Activity activity) {
        this.activity = activity;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(activity.getString(R.string.downloading_workers));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (OkHttpTools.ping(activity)){
            return OkHttpTools.get("getWorkers.htm?key=" + key, activity);
        } else{
            return "";
        }


    }

    @Override
    protected void onPostExecute(String resp) {
        super.onPostExecute(resp);
        progressDialog.dismiss();

        if(!resp.equals("")){
            try {
                JSONObject json = new JSONObject(resp);

                if (json.getString("status").equals("OK")) {
                    loadStep2(json.getString("workers"));
                } else if (json.getString("status").equals("error")) {
                    DialogsTools.launchCustomDialog(activity, json.getString("message"));
                } else {
                    DialogsTools.launchServerDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                DialogsTools.launchCustomDialog(activity, resp);
            }
        } else{
            DialogsTools.launchServerDialog(activity);
        }


    }

    public void loadStep2(String json) {
        activity.setContentView(R.layout.activity_configuration_step_2);

        final Spinner spinnerWorkers, spinnerRate;
        final EditText namePda, nameFavorite;
        Button finish;

        // WORKERS
        spinnerWorkers = (Spinner)activity.findViewById(R.id.spinnerWorkersConfiguration);

        try {
            JSONArray workers = new JSONArray(json);

            List<Worker> listWorkers = new ArrayList<>();

            for (int i = 0; i < workers.length(); i++) {

                JSONArray jsonSubArray = workers.getJSONArray(i);

                listWorkers.add(new Worker(
                        jsonSubArray.getString(__WORKER_ID),
                        jsonSubArray.getString(__WORKER_PASS),
                        jsonSubArray.getString(__WORKER_NAME)));

            }

            String[] workersName = new String[listWorkers.size()];

            for (int i = 0; i < workersName.length; i++) {
                workersName[i] = listWorkers.get(i).getName()+" - "+listWorkers.get(i).getId();
            }

            spinnerWorkers.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, workersName));
        } catch (JSONException e) {
            e.printStackTrace();
            DialogsTools.launchCustomDialog(activity, json);
        }

        // RATES
        spinnerRate = (Spinner) activity.findViewById(R.id.spinnerRateConfiguration);
        spinnerRate.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, new String[] {"1","2","3","4"}));

        // TERMINAL
        namePda = (EditText) activity.findViewById(R.id.inputPdaConfiguration);

        // FAVORITE
        nameFavorite = (EditText) activity.findViewById(R.id.inputFavoriteConfiguration);

        finish = (Button) activity.findViewById(R.id.buttonFinishConfiguration);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerWorkers.getAdapter().getCount() > 0){
                    PreferencesTools.savePreferences(activity, "worker", spinnerWorkers.getSelectedItem().toString());
                    PreferencesTools.savePreferences(activity, "rate", spinnerRate.getSelectedItem().toString());
                    PreferencesTools.savePreferences(activity, "namePda", namePda.getText().toString());

                    if(!nameFavorite.getText().toString().equals("")){

                        Database db = new Database(activity);

                        Connection c = new Connection(0, nameFavorite.getText().toString(), PreferencesTools.getValueOfPreferences(activity, "url"), key, spinnerWorkers.getSelectedItem().toString(), spinnerRate.getSelectedItem().toString(), namePda.getText().toString());

                        long id = new ConnectionsCrud(db).insertC(c);

                        PreferencesTools.savePreferences(activity, "favorite", Long.toString(id));
                    }

                    activity.startActivity(new Intent(activity, Splash.class));

                    activity.finish();
                } else{
                    Snackbar.make(activity.findViewById(R.id.relativeConfiguration), activity.getResources().getString(R.string.select_worker), Snackbar.LENGTH_LONG).show();
                }

            }
        });



    }
}
