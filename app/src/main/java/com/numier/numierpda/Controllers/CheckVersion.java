package com.numier.numierpda.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.numier.numierpda.R;
import com.numier.numierpda.Tools.OkHttpTools;
import com.numier.numierpda.Tools.PreferencesTools;

public class CheckVersion extends AsyncTask<Void, Void, Boolean> {

    Activity activity;
    String url, key;
    ProgressDialog progress;

    public CheckVersion(Activity activity) {
        this.activity = activity;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progress = new ProgressDialog(activity);

        progress.setTitle(activity.getString(R.string.loading));

        progress.setMessage(activity.getString(R.string.checking_last_version));

        progress.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String serial = PreferencesTools.getValueOfPreferences(activity, "serialTPV");

        String resp = OkHttpTools.getVersion("https://www.numier.com/clientes/v1.3/nsync/checkVersionPDA.php?serial="+serial+"#", activity);

        if(resp.equals("OK")){
            return true;
        } else{
            return false;
        }



    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        progress.dismiss();

        if(result){
            new DownloadVersion(activity).execute("https://www.numier.com/pda.apk");
        } else{
            Toast.makeText(activity, activity.getString(R.string.no_new_version), Toast.LENGTH_LONG).show();
        }
    }

}
