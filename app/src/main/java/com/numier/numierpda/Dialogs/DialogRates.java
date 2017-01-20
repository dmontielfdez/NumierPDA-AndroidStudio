package com.numier.numierpda.Dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.Models.Subproduct;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.IntakeTools;

public class DialogRates extends DialogFragment implements View.OnClickListener {

    private Product product;
    private String[] rates;
    private Detalle detail;
    private Button cancel;
    ListView listView;

    public DialogRates(String[] rates, Product product, Detalle detail) {
        this.rates = rates;
        this.product = product;
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

            d.setTitle(getString(R.string.select_rate));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_rates, container, false);

        cancel = (Button) v.findViewById(R.id.buttonCancelDialogRates);
        this.cancel.setOnClickListener(this);

        listView = (ListView) v.findViewById(R.id.listviewRates);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, rates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String rate = rates[pos];
                rate = rate.substring(0, rate.lastIndexOf("-") - 1);

                // Le asigno la tarifa a detalle
                detail.setRate(rate);

                // Le asigno el precio a detalle
                detail.setPrice(getPriceOfValue(product, rate));

                getDialog().cancel();

                IntakeTools.launchDialogMessage(product, detail);
            }
        });

        return v;
    }


    public static double getPriceOfValue(Product product, String value) {

        if (value.equals(product.getRateName1())) {
            return product.getRate1();
        } else if (value.equals(product.getRateName2())) {
            return product.getRate2();
        } else if (value.equals(product.getRateName3())) {
            return product.getRate3();
        } else if (value.equals(product.getRateName4())) {
            return product.getRate4();
        } else {
            return product.getRate1();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCancelDialogRates:
                getDialog().cancel();
                break;
        }
    }
}