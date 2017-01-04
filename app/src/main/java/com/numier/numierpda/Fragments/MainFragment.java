package com.numier.numierpda.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.numier.numierpda.Activities.SettingsActivity;
import com.numier.numierpda.Controllers.Init;
import com.numier.numierpda.DB.ConnectionsCrud;
import com.numier.numierpda.DB.Database;
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
