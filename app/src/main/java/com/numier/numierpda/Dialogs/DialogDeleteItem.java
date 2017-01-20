package com.numier.numierpda.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Fragments.CashFragment;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.List;

public class DialogDeleteItem extends DialogFragment {

    private List<Detalle> details;
    private int position;

    public DialogDeleteItem(List<Detalle> details, int position) {
        this.details = details;
        this.position = position;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.info_delete_item)
                .setTitle(getString(R.string.attention))
                .setPositiveButton(getString(R.string.accept),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Database db = new Database(getActivity());

                                String forceDinners = PreferencesTools.getValueOfPreferences(getActivity(), "forceDinners");

                                if(forceDinners.equals("S")){
                                    String dinnerProduct = PreferencesTools.getValueOfPreferences(getActivity(), "dinnerProduct");

                                    Product p = new ProductCrud(db).findById(dinnerProduct);

                                    if(p != null){
                                        if(p.getId().equals(details.get(position).getIdProduct())){
                                            CashFragment.numDinners.setText("0");
                                        }
                                    }
                                }

                                details.remove(position);

                                Cash.getTicketFragment().getAdapterList().notifyDataSetChanged();

                                if(details.size() > 0){
                                    Cash.getCashFragment().setDetailsToHeader(details, details.get(0));
                                } else{
                                    Cash.getCashFragment().setDetailsToHeader(details, new Detalle());
                                }

                                dialog.cancel();
                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }
}
