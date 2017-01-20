package com.numier.numierpda.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.R;

public class DialogAlertGetBill extends DialogFragment {

    public DialogAlertGetBill() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.attention))
                .setMessage(R.string.alert_get_bill_and_save)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        ((Cash) getActivity()).getCashFragment().launchDialogSaveBill();
                        dialog.cancel();

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Cash.getCashFragment().launchGetBill(1);

                        dialog.cancel();

                    }
                });

        return builder.create();
    }
}
