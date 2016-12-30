package com.numier.numierpda.Tools;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.numier.numierpda.R;


public class DialogsTools {
    public static void launchNetworkDialog(Activity context, int stateWifi) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(context.getResources().getString(R.string.attention));

        String nameWifi = PreferencesTools.getValueOfPreferences(context, "nombreWifi");

        String cadenaWifi = "";
        if (!nameWifi.equals("")) {
            String[] arrayWifi = nameWifi.split(",");
            cadenaWifi += " y conectese a una de las siguientes redes WIFI:\n\n";
            for (int i = 0; i < arrayWifi.length; i++) {
                cadenaWifi += "- " + arrayWifi[i] + "\n";
            }
        }

        switch (stateWifi) {
            case 0:
                alertDialog.setMessage("La conexión WIFI del dispositivo está desactivada. Actívela" + cadenaWifi);
                break;
            case 1:
                alertDialog.setMessage("No está conectado a ninguna red WIFI. Vaya a la configuración de su dispositivo" + cadenaWifi);
                break;
            case 2:
                alertDialog.setMessage("Está conectado a una red WIFI incorrecta. Vaya a la configuración de su dispositivo" + cadenaWifi);
                break;

            default:
                break;
        }

        // Setting Dialog Message


        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_no_wifi);

        alertDialog.setButton(context.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static void launchServerDialog(Activity context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(context.getResources().getString(R.string.attention));

        // RECUPERO IP DEL SERVIDOR Y PDA
        String[] ip = NetworkTools.getIp(context).split("\\.");
        String[] ipSever = NetworkTools.getUrl(context).split("\\.");

        ipSever[3] = ipSever[3].substring(0, ipSever[3].indexOf(":"));

        // RECUPERO POTENCIA WIFI

        int levelWifi = NetworkTools.checkNetworkStrengh(context);

        if (!ip[0].equals(ipSever[0]) || !ip[1].equals(ipSever[1]) || !ip[2].equals(ipSever[2])) {
            alertDialog.setMessage(context.getResources().getString(R.string.server_and_pda_distinc_net) + "\n\n"
                    + "- " + context.getResources().getString(R.string.ip_pda) + " " + NetworkTools.getIp(context) + "\n"
                    + "- " +  context.getResources().getString(R.string.ip_server) + " " + ipSever[0] + "." + ipSever[1] + "." + ipSever[2] + "." + ipSever[3]);
        } else if (ip.equals(ipSever)) {
            alertDialog.setMessage(context.getResources().getString(R.string.server_and_pda_equals_ip));
        } else if (levelWifi <= 1) {
            alertDialog.setMessage(context.getResources().getString(R.string.level_pda_low));
        } else {
            alertDialog.setMessage(context.getResources().getString(R.string.connect_server_fail));
        }


        alertDialog.setIcon(R.mipmap.ic_alert);

        alertDialog.setButton(context.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }


    public static void launchCustomDialog(Activity context, String error) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(context.getResources().getString(R.string.attention));

        alertDialog.setMessage(error);
        alertDialog.setIcon(R.mipmap.ic_alert);

        alertDialog.setButton(context.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }


}
