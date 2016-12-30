package com.numier.numierpda.Fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.numier.numierpda.Activities.Configuration;
import com.numier.numierpda.Activities.Main;
import com.numier.numierpda.DB.ConnectionsCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.Dialogs.DialogFavorites;
import com.numier.numierpda.Models.Connection;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

public class WelcomeFragment extends Fragment implements OnClickListener {

    private Button connect;
    private ImageButton favorites;
    private View view;

    public WelcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.view = view;
        connect = (Button) view.findViewById(R.id.buttonConnect);
        favorites = (ImageButton) view.findViewById(R.id.buttonFavorites);

        connect.setOnClickListener(this);
        favorites.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonConnect:

                // Si no hay ninguna URL guardada comienzo proceso de configuracion
                if (PreferencesTools.getValueOfPreferences(getActivity(), "configured").equals("")){
                    startActivity(new Intent(getActivity(), Configuration.class));
                }
                else{
                    Intent intent = new Intent(getActivity(), Main.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }

                break;

            case R.id.buttonFavorites:
                getFavorites();
                break;

        }
    }


    public void getFavorites(){
        Database db = new Database(getActivity());

        ArrayList<Connection> connections = (ArrayList<Connection>) new ConnectionsCrud(db).getAll();

        if(connections.size() == 0){
            Snackbar.make(view, getResources().getText(R.string.no_favorite_connections), Snackbar.LENGTH_LONG).show();
        } else{
            new DialogFavorites(getActivity(), connections).show(getActivity().getSupportFragmentManager(), "dialogConnection");
        }


    }





}
