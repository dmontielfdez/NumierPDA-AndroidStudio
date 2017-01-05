package com.numier.numierpda.Controllers;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadVersion extends AsyncTask<String, String, Boolean> {
    String path = "/sdcard/pda.apk";

    Activity activity;
    String url, key;
    ProgressDialog progress;

    public DownloadVersion(Activity activity) {
        this.activity = activity;
        key = PreferencesTools.getValueOfPreferences(activity, "key");
        url = PreferencesTools.getValueOfPreferences(activity, "base_url");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progress = new ProgressDialog(activity);

        progress.setTitle(activity.getString(R.string.loading));

        progress.setMessage(activity.getString(R.string.downloading_last_version));

        progress.setProgressStyle(progress.STYLE_HORIZONTAL);
        progress.setProgress(0);
        progress.show();
    }

    protected Boolean doInBackground(String... sUrl) {
        try {
            URL url = new URL(sUrl[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = 2492213;

            progress.setMax(fileLength);

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(path);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {

                total += count;
                progress.setProgress((int) (total));
                output.write(data, 0, count);
            }


            output.flush();
            output.close();
            input.close();

            return true;
        } catch (Exception e) {
            progress.cancel();
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean status) {
        progress.cancel();
        if (status) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            activity.startActivity(i);
        } else {
            Toast.makeText(activity, activity.getString(R.string.fail_update), Toast.LENGTH_LONG).show();
        }

    }
}
