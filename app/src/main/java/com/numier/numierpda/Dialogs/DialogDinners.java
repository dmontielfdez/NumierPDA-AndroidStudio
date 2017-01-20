package com.numier.numierpda.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Fragments.CashFragment;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

public class DialogDinners extends DialogFragment implements View.OnClickListener {

	private StringBuilder countInteger;
	private int numDinners;
	private Button one, two, three, four, five, six, seven, eight, nine, zero, ce, ok, cancel;
	private TextView valueDialogKeyboard;

	public DialogDinners(int numDinners) {
		this.numDinners = numDinners;
		this.countInteger = new StringBuilder("");
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

			d.setTitle(getString(R.string.num_dinners));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_keyboard, container, false);

		cancel = (Button) v.findViewById(R.id.buttonCancelDialogKeyboard);
		one = (Button) v.findViewById(R.id.buttonOneDialogKeyboard);
		two = (Button) v.findViewById(R.id.buttonTwoDialogKeyboard);
		three = (Button) v.findViewById(R.id.buttonThreeDialogKeyboard);
		four = (Button) v.findViewById(R.id.buttonFourDialogKeyboard);
		five = (Button) v.findViewById(R.id.buttonFiveDialogKeyboard);
		six = (Button) v.findViewById(R.id.buttonSixDialogKeyboard);
		seven = (Button) v.findViewById(R.id.buttonSevenDialogKeyboard);
		eight = (Button) v.findViewById(R.id.buttonEightDialogKeyboard);
		nine = (Button) v.findViewById(R.id.buttonNineDialogKeyboard);
		zero = (Button) v.findViewById(R.id.buttonZeroDialogKeyboard);
		ce = (Button) v.findViewById(R.id.buttonCeDialogKeyboard);
		ok = (Button) v.findViewById(R.id.buttonOkDialogKeyboard);
		cancel = (Button) v.findViewById(R.id.buttonCancelDialogKeyboard);
		valueDialogKeyboard = (TextView) v.findViewById(R.id.valueDialogKeyboard);

		valueDialogKeyboard.setText(numDinners + "");

		this.cancel.setOnClickListener(this);
		this.one.setOnClickListener(this);
		this.two.setOnClickListener(this);
		this.three.setOnClickListener(this);
		this.four.setOnClickListener(this);
		this.five.setOnClickListener(this);
		this.six.setOnClickListener(this);
		this.seven.setOnClickListener(this);
		this.eight.setOnClickListener(this);
		this.nine.setOnClickListener(this);
		this.zero.setOnClickListener(this);
		this.ce.setOnClickListener(this);
		this.ok.setOnClickListener(this);
		this.cancel.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.buttonOneDialogKeyboard:
				setNewValue(1);
				break;
			case R.id.buttonTwoDialogKeyboard:
				setNewValue(2);

				break;
			case R.id.buttonThreeDialogKeyboard:
				setNewValue(3);

				break;
			case R.id.buttonFourDialogKeyboard:
				setNewValue(4);

				break;
			case R.id.buttonFiveDialogKeyboard:
				setNewValue(5);

				break;
			case R.id.buttonSixDialogKeyboard:
				setNewValue(6);

				break;
			case R.id.buttonSevenDialogKeyboard:
				setNewValue(7);

				break;
			case R.id.buttonEightDialogKeyboard:
				setNewValue(8);

				break;
			case R.id.buttonNineDialogKeyboard:
				setNewValue(9);

				break;
			case R.id.buttonZeroDialogKeyboard:
				setNewValue(0);

				break;

			case R.id.buttonCeDialogKeyboard:
				valueDialogKeyboard.setText("0");
				this.countInteger = new StringBuilder("");

				break;

			case R.id.buttonOkDialogKeyboard:
				CashFragment.numDinners.setText(valueDialogKeyboard.getText().toString());

				String dinnerProduct = PreferencesTools.getValueOfPreferences(getActivity(), "dinnerProduct");
				if (!dinnerProduct.equals("")) {
					Database db = new Database(getActivity());

					Product p = new ProductCrud(db).findById(dinnerProduct);

					if (p != null) {
//					IntakeUtils.generateIntake(getActivity(), p, Integer.parseInt(valueDialogKeyboard.getText().toString()), "", 1, false);
					}

				}


				getDialog().cancel();

				break;
			case R.id.buttonCancelDialogKeyboard:
				getDialog().cancel();
				break;
		}
	}

	private void setNewValue(int value) {
		if (countInteger.length() == 6){
			countInteger = new StringBuilder(countInteger.substring(0, 5));
		}

		countInteger.append(Integer.toString(value));

		valueDialogKeyboard.setText(countInteger.toString());

	}
}

