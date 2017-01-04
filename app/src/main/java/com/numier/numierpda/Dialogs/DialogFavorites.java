package com.numier.numierpda.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import com.numier.numierpda.Activities.Splash;
import com.numier.numierpda.Adapters.AdapterConnections;
import com.numier.numierpda.DB.ConnectionsCrud;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.Models.Connection;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.ArrayList;

public class DialogFavorites extends DialogFragment {

    ListView listview;
    AdapterConnections adapter;
    ArrayList<Connection> listConnections;
    private Activity context;


    public DialogFavorites(Activity context, ArrayList<Connection> listConnections) {
        this.context = context;
        this.listConnections = listConnections;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());

        dialog.setTitle(getResources().getText(R.string.select_favorite_connections));
        dialog.getWindow().setGravity(Gravity.CENTER);

        View v = context.getLayoutInflater().inflate(R.layout.dialog_favorite_connection, null);

        listview = (ListView) v.findViewById(R.id.listConnections);

        adapter = new AdapterConnections(context, listConnections);
        listview.setAdapter(adapter);

        registerForContextMenu(listview);

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferencesTools.savePreferences(context, "base_url", "http://"+listConnections.get(position).getUrl()+"/controllers/");
                PreferencesTools.savePreferences(context, "url", listConnections.get(position).getUrl());
                PreferencesTools.savePreferences(context, "key", listConnections.get(position).getKey());
                PreferencesTools.savePreferences(context, "worker", listConnections.get(position).getOperario());
                PreferencesTools.savePreferences(context, "rate", listConnections.get(position).getRate());
                PreferencesTools.savePreferences(context, "namePda", listConnections.get(position).getTerminal());
                PreferencesTools.savePreferences(context, "favorite", listConnections.get(position).getId() + "");

                startActivity(new Intent(context, Splash.class));
                context.finish();
            }
        });

        if (v != null) {
            dialog.setContentView(v);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        dialog.show();

        return dialog;

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        MenuInflater inflater = context.getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(listConnections.get(info.position).getName());

        inflater.inflate(R.menu.menu_list_item_connection, menu);

        OnMenuItemClickListener listener = new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onContextItemSelected(item);
                return true;
            }
        };

        for (int i = 0, n = menu.size(); i < n; i++)
            menu.getItem(i).setOnMenuItemClickListener(listener);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.deleteConnectionn:
                Database db = new Database(getActivity());
                int result = new ConnectionsCrud(db).deleteConnection(Integer.toString(listConnections.get(info.position).getId()));

                if (result == 1) {
                    listConnections.remove(info.position);
                    adapter.notifyDataSetChanged();

                    if(listConnections.size() == 0){
                        getDialog().dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getText(R.string.delete_fail_connection), Toast.LENGTH_LONG).show();
                }

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


}
