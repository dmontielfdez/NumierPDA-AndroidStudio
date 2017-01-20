package com.numier.numierpda.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.numier.numierpda.R;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.NetworkTools;
import com.numier.numierpda.Tools.OkHttpTools;
import com.numier.numierpda.Tools.PreferencesTools;

import org.json.JSONException;
import org.json.JSONObject;


public class CheckKey extends AsyncTask<Void, Void, String> {

    Activity activity;
    String url, key;
    ProgressDialog progressDialog;

    public CheckKey(Activity activity) {
        this.activity = activity;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(activity.getResources().getString(R.string.connecting_server));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (OkHttpTools.ping(activity)){
            return OkHttpTools.get("key.htm?key=" + key, activity);
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
                    new GetWorkers(activity).execute();
                } else if(json.getString("status").equals("error")){
                    DialogsTools.launchCustomDialog(activity, json.getString("message"));
                } else{
                    DialogsTools.launchServerDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                DialogsTools.launchCustomDialog(activity, e.getMessage()+" - "+resp);
            }
        } else{
            DialogsTools.launchServerDialog(activity);
        }


    }
}
