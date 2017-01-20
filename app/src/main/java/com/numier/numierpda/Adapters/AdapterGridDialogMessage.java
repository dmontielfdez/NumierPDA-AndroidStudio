package com.numier.numierpda.Adapters;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.numier.numierpda.R;

public class AdapterGridDialogMessage extends ArrayAdapter<String> {

    // View
    private TextView valueDialogMessage;

    // Atributos
    private String[] listMessages;
    private Activity context;

    public AdapterGridDialogMessage(Activity context, String[] objects) {
        super(context, R.layout.item_dialog_message, objects);
        this.listMessages = objects;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = context.getLayoutInflater().inflate(R.layout.item_dialog_message, null);

        valueDialogMessage = (TextView) v.findViewById(R.id.valueItemDialogMessage);

        valueDialogMessage.setText(listMessages[position]);

        return v;
    }

}
