package com.numier.numierpda.Tools;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;


/**
 * Created by dmontielfdez on 23/12/2016.
 */

public class OkHttpTools {

    public OkHttpTools(){
    }

    public static boolean ping(Activity activity) {

        String URL = PreferencesTools.getValueOfPreferences(activity, "url");

        try{
            Builder b = new Builder();
            b.connectTimeout(500, TimeUnit.MILLISECONDS);
            OkHttpClient client = b.build();

            Request request = new Request.Builder()
                    .url("http://"+URL)
                    .build();

            Response response = client.newCall(request).execute();

            if(response.code() == 200){
                return true;
            } else{
                return false;
            }

        } catch (ConnectException e){
            e.printStackTrace();
            return false;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static String get(String url, Activity activity) {

        String resp = "";

        String BASE_URL = PreferencesTools.getValueOfPreferences(activity, "base_url");

        try{
            Log.d("url", BASE_URL+url);

            Builder b = new Builder();
            b.connectTimeout(20000, TimeUnit.MILLISECONDS);
            OkHttpClient client = b.build();

            Request request = new Request.Builder()
                    .url(BASE_URL+url)
                    .build();

            Response response = client.newCall(request).execute();

            resp = response.body().string();

        } catch (ConnectException e){
            e.printStackTrace();
            resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"ConnectException\"}";
        } catch (IOException e){
            e.printStackTrace();
            resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"IOException\"}";
        } catch (Exception e){
            e.printStackTrace();
            resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"Exception\"}";
        }

        return resp;
    }

    public static String getVersion(String url, Activity activity) {

        String resp = "";

        try{

            Builder b = new Builder();
            b.connectTimeout(20000, TimeUnit.MILLISECONDS);
            OkHttpClient client = b.build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();

            resp = response.body().string();

        } catch (ConnectException e){
            e.printStackTrace();
            resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"ConnectException\"}";
        } catch (IOException e){
            e.printStackTrace();
            resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"IOException\"}";
        } catch (Exception e){
            e.printStackTrace();
            resp = "{\"mod\" : \"init\", \"status\" : \"ERROR\", \"message\" : \"Exception\"}";
        }

        return resp;
    }
}
