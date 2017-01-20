package com.numier.numierpda.Dialogs;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Adapters.AdapterGridDialogMessage;
import com.numier.numierpda.Fragments.TicketFragment;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.IntakeTools;

public class DialogAskMessage extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String[] listMessages;
    private Detalle detail;

    private GridView gridview;
    private TextView textview;
    private Button accept, back;

    public DialogAskMessage(String[] messages, Detalle detail) {
        this.listMessages = messages;
        this.detail = detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);

            d.setTitle(getString(R.string.input_message_product));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_message, container, false);

        gridview = (GridView) v.findViewById(R.id.gridViewDialogMessage);
        accept = (Button) v.findViewById(R.id.buttonAcceptMessageDialog);
        back = (Button) v.findViewById(R.id.buttonCancelMessageDialog);
        textview = (TextView) v.findViewById(R.id.inputMessageDialogMessage);

        this.accept.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.gridview.setOnItemClickListener(this);

        gridview.setAdapter(new AdapterGridDialogMessage(getActivity(), listMessages));

        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonCancelMessageDialog:
                getDialog().cancel();
                break;

            case R.id.buttonAcceptMessageDialog:

                this.detail.setOption(this.textview.getText().toString());

                IntakeTools.addDetailToList(detail);

                getDialog().cancel();

                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        textview.setText(listMessages[arg2]);
        this.detail.setOption(this.textview.getText().toString());

        IntakeTools.addDetailToList(detail);

        getDialog().cancel();
    }

}
