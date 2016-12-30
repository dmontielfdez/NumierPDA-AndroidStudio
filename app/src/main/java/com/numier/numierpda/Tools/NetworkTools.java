package com.numier.numierpda.Tools;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * Utilidades de red(disponibilidad, si hay conexión o no, y qué url está usando el móvil).
 */
public class NetworkTools {

    public static String getUrl(Activity context) {
        return PreferencesTools.getValueOfPreferences(context, "url");
    }

    public static String getIp(Activity activity) {
        String sinWifi = PreferencesTools.getValueOfPreferences(activity, "sinWifi");

        if (sinWifi.equals("N") || sinWifi.equals("")) {
            WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
            int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

            // Convert little-endian to big-endianif needed
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ipAddress = Integer.reverseBytes(ipAddress);
            }

            byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

            String ipAddressString;
            try {
                ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
            } catch (UnknownHostException ex) {
                ipAddressString = "No es posible detectar la ip";
            }
            return ipAddressString;

        } else {
            return "";
        }

    }

    public static int checkNetworkStrengh(Activity activity) {
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        int numberOfLevels = 5;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

        return level;
    }
}
