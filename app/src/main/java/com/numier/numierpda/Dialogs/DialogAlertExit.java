package com.numier.numierpda.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.numier.numierpda.Activities.Welcome;
import com.numier.numierpda.R;

public class DialogAlertExit extends DialogFragment {

	public DialogAlertExit() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(getString(R.string.attention))
				.setMessage(getString(R.string.alert_save_bill))
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
//								CashFragment.setAccount();
							}
						})
				.setNegativeButton(R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent(getActivity(), Welcome.class);
								startActivity(i);
								getActivity().finish();
								dialog.cancel();

							}
						});

		return builder.create();
	}
}