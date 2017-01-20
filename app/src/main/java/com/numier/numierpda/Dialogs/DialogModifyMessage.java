package com.numier.numierpda.Dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Adapters.AdapterGridDialogMessage;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;
import com.numier.numierpda.Tools.IntakeTools;

public class DialogModifyMessage extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String[] listMessages;
    private Detalle detail;
    private int position;
    private GridView gridview;
    private TextView textview;
    private Button accept, back;

    public DialogModifyMessage(String[] messages, Detalle detail, int position) {
        this.listMessages = messages;
        this.detail = detail;
        this.position = position;
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

                Cash.getTicketFragment().getDetails().get(position).setOption(this.textview.getText().toString());

//                Detalle d = Cash.getTicketFragment().getDetails().get(position);
//
//                String title = d.getProductName();
//
//                String nameRate = ConversionTools.getNameRate(getActivity(), d);
//                String nameSubproducts = ConversionTools.getNameSubproducts(getActivity(), d.getListSubProducts(), d.getIdProduct());
//
//                if (nameRate != "") {
//                    title += " " + nameRate;
//                }
//
//                if (nameSubproducts != "") {
//                    title += " " + nameSubproducts;
//                }
//
//                if (d.getOption() != null && !d.getOption().equals("")) {
//                    title += " - " + d.getOption();
//                }
//
//
//                Cash.getTicketFragment().getDetails().get(position).setTitle(title);

                Cash.getTicketFragment().getAdapterList().notifyDataSetChanged();

                getDialog().cancel();

                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        textview.setText(listMessages[arg2]);

        Cash.getTicketFragment().getDetails().get(position).setOption(this.textview.getText().toString());

//        Detalle d = Cash.getTicketFragment().getDetails().get(position);
//
//        String title = d.getProductName();
//
//        String nameRate = ConversionTools.getNameRate(getActivity(), d);
//        String nameSubproducts = ConversionTools.getNameSubproducts(getActivity(), d.getListSubProducts(), d.getIdProduct());
//
//        if (nameRate != "") {
//            title += " " + nameRate;
//        }
//
//        if (nameSubproducts != "") {
//            title += " " + nameSubproducts;
//        }
//
//        Log.d("hola", d.getOption());
//
//        if (d.getOption() != null && !d.getOption().equals("")) {
//            title += " - " + d.getOption();
//        }
//
//
//        Cash.getTicketFragment().getDetails().get(position).setTitle(title);

        Cash.getTicketFragment().getAdapterList().notifyDataSetChanged();

        getDialog().cancel();
    }

}
