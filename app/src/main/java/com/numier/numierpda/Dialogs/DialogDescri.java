package com.numier.numierpda.Dialogs;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Fragments.CashFragment;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.List;

public class DialogDescri extends DialogFragment {


    private Database db;
    private Activity context;
    private List<Detalle> details;
    private int position;


    public DialogDescri(Activity context, List<Detalle> details, int position) {
        this.context = context;
        this.details = details;
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        db = new Database(context);

        String name = new ProductCrud(db).findById(details.get(position).getIdProduct()).getName();
        String descriText = new ProductCrud(db).findById(details.get(position).getIdProduct()).getDescri();

        if (descriText.equals("")) {
            descriText = getString(R.string.product_without_descripction);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(descriText)
                .setTitle(name)
                .setPositiveButton(getString(R.string.accept),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

        return builder.create();

    }


}
