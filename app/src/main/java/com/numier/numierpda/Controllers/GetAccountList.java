package com.numier.numierpda.Controllers;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.numier.numierpda.Dialogs.DialogGetAccountList;
import com.numier.numierpda.Models.Header;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.OkHttpTools;
import com.numier.numierpda.Tools.PreferencesTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.numier.numierpda.Controllers.NumierApi.__BILL_AMOUNT;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_DINNER;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_ID;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_LOCKED;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_NAME;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_NUMBER;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_PRINTED;
import static com.numier.numierpda.Controllers.NumierApi.__BILL_PRINTERS;

public class GetAccountList extends AsyncTask<Void, Void, String> {

    private int option;

    FragmentActivity activity;
    String key, url;
    ProgressDialog progressDialog;

    public GetAccountList(FragmentActivity activity, int option) {
        this.option = option;
        this.activity = activity;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(activity.getString(R.string.wait_moment));
        progressDialog.setMessage(activity.getString(R.string.getting_accounts));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (OkHttpTools.ping(activity)){
            return OkHttpTools.get("getAccountList.htm?key=" + key, activity);
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
                    parseJson(json.toString());
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

    public void parseJson(String json){

        List<Header> listAccounts;
        try {

            JSONObject responseJSON = new JSONObject(json);

            JSONArray accounts = responseJSON.getJSONArray("accounts");

            listAccounts = new ArrayList<Header>();

            for (int i = 0; i < accounts.length(); i++) {

                JSONArray jsonSubArray = accounts.getJSONArray(i);

                JSONArray jsonPrinters = jsonSubArray.getJSONArray(__BILL_PRINTERS);

                String[] printers = null;
                if(option==2){
                    printers = new String[jsonPrinters.length()];
                    for (int j = 0; j < jsonPrinters.length(); j++) {
                        printers[j] = jsonPrinters.getString(j);
                    }
                }

                listAccounts.add(new Header(
                        jsonSubArray.getInt(__BILL_ID),
                        jsonSubArray.getInt(__BILL_NUMBER),
                        jsonSubArray.getString(__BILL_NAME),
                        jsonSubArray.getInt(__BILL_DINNER),
                        jsonSubArray.getDouble(__BILL_AMOUNT),
                        jsonSubArray.getInt(__BILL_PRINTED),
                        jsonSubArray.getInt(__BILL_LOCKED),
                        0,
                        0,
                        0,
                        0,
                        0,
                        printers));
            }

            new DialogGetAccountList(listAccounts, option).show(activity.getSupportFragmentManager(), "Dialog");

        } catch (Exception e) {
            e.printStackTrace();
            DialogsTools.launchCustomDialog(activity, json);
        }

    }
}


