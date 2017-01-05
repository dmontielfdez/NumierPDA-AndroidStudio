package com.numier.numierpda.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Activities.SettingsActivity;
import com.numier.numierpda.Controllers.Init;
import com.numier.numierpda.DB.ConnectionsCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.SelingSubproductCrud;
import com.numier.numierpda.DB.WorkerCrud;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;


public class MainFragment extends Fragment implements View.OnClickListener {

    private Button botonCaja, botonConfig, botonSalir;
    private Database db;
    private TextView rate;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        botonCaja = (Button) view.findViewById(R.id.cash);
        botonConfig = (Button) view.findViewById(R.id.conf);
        botonSalir = (Button) view.findViewById(R.id.exit);
        rate = (TextView) view.findViewById(R.id.rateMainFragment);

        botonCaja.setOnClickListener(this);
        botonConfig.setOnClickListener(this);
        botonSalir.setOnClickListener(this);

        db = new Database(getActivity());

        String connection = PreferencesTools.getValueOfPreferences(getActivity(), "connection");

        if(!connection.equals("")){
            String name = new ConnectionsCrud(db).getNameConnection(connection);

            connection = name + " - ";
        }

        rate.setText(connection + "Tarifa " + (PreferencesTools.getValueOfPreferences(getActivity(),"rate")));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cash:
                String elegirMesa = PreferencesTools.getValueOfPreferences(getActivity(), "elegirMesa");

                new SelingSubproductCrud(db).deleteAll();

                if(elegirMesa.equals("S")){
//                    startActivity(new Intent(getActivity(), ElegirMesa.class));
//                    getActivity().finish();
                } else{

                    Log.d("entra", "entra");
                    final String operator = PreferencesTools.getValueOfPreferences(getActivity(), "operator");

                    Database db = new Database(getActivity());
                    final String[] datos = new WorkerCrud(db).getPwd(operator);

                    // Si pwd es null inicio actividad
                    if(datos[1] == null){
                        startActivity(new Intent(getActivity(), Cash.class));
                        getActivity().finish();
                    } else{
                        if(datos[1].equals("") || datos[1].equals(null)){
                            startActivity(new Intent(getActivity(), Cash.class));
                            getActivity().finish();
                        } else{
                            // Cargo el dialog para pedir contraseña
//                            View layoutDialogo = getActivity().getLayoutInflater().inflate(R.layout.dial_password_true, null);
//
//                            final EditText escribePassAqui = (EditText) layoutDialogo.findViewById(R.id.etPassword);
//
//                            AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(getActivity());
//                            constructorDialogo.setTitle("Introduzca Contraseña de "+datos[0]);
//                            constructorDialogo.setView(layoutDialogo);
//                            constructorDialogo.setCancelable(false).setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//                                    // TODO Auto-generated method stub
//
//                                    // se comprueba si el usuario ha escrito
//                                    if (escribePassAqui.getText().toString() != null && !escribePassAqui.getText().toString().equalsIgnoreCase("")) {
//                                        String contrasenyaEscrita = escribePassAqui.getText().toString();
//                                        // si lo que escribió y la contrasenya
//                                        // son correctas
//                                        if (contrasenyaEscrita.equals(datos[1])) {
//                                            startActivity(new Intent(getActivity(), Cash.class));
//                                            getActivity().finish();
//                                        } else{
//                                            Toast.makeText(getActivity(), "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                }
//                            })
//                                    .setNegativeButton("Cancelar",
//                                            new DialogInterface.OnClickListener() {
//
//                                                @Override
//                                                public void onClick(DialogInterface dialog,	int which) {
//                                                    // TODO Auto-generated method stub
//                                                    dialog.cancel();
//                                                }
//                                            });
//
//                            AlertDialog dialogo = constructorDialogo.create();
//                            dialogo.show();
                        }
                    }
                }
                break;
            case R.id.conf:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                new DialogExit().show(getActivity().getSupportFragmentManager(), "dialogo");
                break;
        }
    }

    public static class DialogExit extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(getActivity().getString(R.string.confirm_cancel))
                    .setTitle("Confirmación")
                    .setPositiveButton(getActivity().getString(R.string.accept),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().finish();
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton(getActivity().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            return builder.create();
        }
    }
}
