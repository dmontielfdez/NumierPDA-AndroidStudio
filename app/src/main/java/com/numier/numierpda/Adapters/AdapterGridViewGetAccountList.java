package com.numier.numierpda.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.numier.numierpda.Models.Header;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;

import java.util.List;

public class AdapterGridViewGetAccountList extends ArrayAdapter<Header> {

    // Atributos
    private Activity activity;
    private List<Header> hearders;

    // View
    private TextView numDinners, numAccount, totalAccount;
    private ImageView imgNumDinners, imgPrinterOk;

    // Constructor
    public AdapterGridViewGetAccountList(Activity context, List<Header> headersList) {
        super(context, R.layout.item_get_account_list, headersList);
        this.activity = context;
        this.hearders = headersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = activity.getLayoutInflater().inflate(R.layout.item_get_account_list, null);

        imgNumDinners = (ImageView) vista.findViewById(R.id.img_num_diners_get_account_list);
        numDinners = (TextView) vista.findViewById(R.id.num_dinners_get_account_list);
        numAccount = (TextView) vista.findViewById(R.id.num_bill_get_account_list);
        totalAccount = (TextView) vista.findViewById(R.id.total_account_get_account_list);
        imgPrinterOk = (ImageView) vista.findViewById(R.id.img_printer_ok_get_account_list);

        if (hearders.get(position).getBillName() != null && !this.hearders.get(position).getBillName().equalsIgnoreCase("")) {
            if (hearders.get(position).getBillName().length() < 10) {
                numAccount.setText(this.hearders.get(position).getBillName());
            } else {
                numAccount.setText(this.hearders.get(position).getBillName().substring(0, 10));
            }
            numAccount.setTextColor(Color.parseColor("#079798"));
        } else {
            numAccount.setText("Nº " + this.hearders.get(position).getNumBill());
        }

        this.totalAccount.setText(ConversionTools.getFormatPrice(this.hearders.get(position).getAmount(), false));

        if (hearders.get(position).getDiners() != 0) {
            imgNumDinners.setVisibility(View.VISIBLE);
            numDinners.setVisibility(View.VISIBLE);
            numDinners.setText(hearders.get(position).getDiners() + "");
        }

        if (hearders.get(position).getPrinted() == 1) {
            imgPrinterOk.setVisibility(View.VISIBLE);
        }

        return vista;
    }
}
