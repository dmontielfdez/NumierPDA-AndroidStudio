package com.numier.numierpda.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.numier.numierpda.Activities.Welcome;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.OkHttpTools;
import com.numier.numierpda.Tools.PreferencesTools;

import org.json.JSONException;
import org.json.JSONObject;


public class Init extends AsyncTask<Void, Void, String> {


    public ProgressDialog progress;
    public Activity activity;
    String key, url;

    public Init(Activity activity) {
        this.activity = activity;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(activity);

        progress.setTitle(activity.getString(R.string.loading));

        progress.setMessage(activity.getString(R.string.splash_info));

        progress.setCancelable(false);
        progress.setProgressStyle(progress.STYLE_SPINNER);
        progress.setProgress(0);
        progress.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (OkHttpTools.ping(activity)){
            return OkHttpTools.get("init.htm?key=" + key, activity);
        } else{
            return "";
        }

    }

    @Override
    protected void onPostExecute(String resp) {

        Intent intent = new Intent(activity, Welcome.class);

        if(!resp.equals("")){
            try {
                JSONObject json = new JSONObject(resp);
                if (json.getString("status").equals("OK")) {
                    new NumierApi(activity, json.toString()).execute();
                    progress.dismiss();
                } else if (json.getString("status").equals("error")) {
                    progress.dismiss();
                    DialogsTools.launchCustomDialog(activity, json.getString("message"));
                } else {
                    progress.dismiss();
                    DialogsTools.launchServerDialog(activity);
                }
            } catch (JSONException e) {
                progress.dismiss();
                DialogsTools.launchCustomDialog(activity, e.getMessage()+" - "+resp);
                e.printStackTrace();

            }
        } else{
            progress.dismiss();
            DialogsTools.launchServerDialog(activity);
        }
    }

}
