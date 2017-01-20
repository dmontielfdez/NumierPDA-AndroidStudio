package com.numier.numierpda.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.IntakeTools;

public class DialogAskWeight extends DialogFragment implements View.OnClickListener {

    private StringBuilder countDecimal, countInteger;
    Product product;
    Detalle detail;
    private boolean decimalActivated;
    private Button one, two, three, four, five, six, seven, eight, nine, zero, ce, ok, cancel, decimal;
    private TextView valueDialogKeyboard;
    View v;

    public DialogAskWeight(Product product, Detalle detail) {
        this.product = product;
        this.detail = detail;
        this.decimalActivated = false;
        this.countDecimal = new StringBuilder("");
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

            d.setTitle(getString(R.string.specify_weight_product));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_keyboard, container, false);

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
        decimal = (Button) v.findViewById(R.id.buttonDecimalDialogKeyboard);
        valueDialogKeyboard = (TextView) v.findViewById(R.id.valueDialogKeyboard);

        valueDialogKeyboard.setText("0.000 €");

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
        this.decimal.setOnClickListener(this);

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
            case R.id.buttonDecimalDialogKeyboard:
                this.decimalActivated = true;

                if (countInteger.length() == 0) {
                    countInteger.append("0");
                }

                break;
            case R.id.buttonCeDialogKeyboard:
                this.decimalActivated = false;
                valueDialogKeyboard.setText("0.000 €");
                this.countDecimal = new StringBuilder("");
                this.countInteger = new StringBuilder("");

                break;

            case R.id.buttonOkDialogKeyboard:
                if (countInteger.length() == 0) {
                    countInteger.append("0");
                }

                while (countDecimal.length() < 3) {
                    countDecimal.append("0");
                }

                double value = Double.parseDouble(countInteger.toString() + countDecimal.toString());

                if(value / 1000 == 0.0){
                    Snackbar.make(v, getString(R.string.weight_not_zero), Snackbar.LENGTH_LONG).show();
                } else{
                    this.detail.setQuantity(value / 1000);

                    IntakeTools.launchDialogRate(product, detail);
                    getDialog().cancel();

                }

                break;
            case R.id.buttonCancelDialogKeyboard:
                getDialog().cancel();
                break;
        }
    }

    private void setNewValue(int value) {
        if (decimalActivated) {
            if (countDecimal.length() == 3){
                countDecimal = new StringBuilder(countDecimal.substring(0, 2));
            }

            switch (countDecimal.length()) {
                case 0:
                    this.valueDialogKeyboard.setText(countInteger + "." + countDecimal.append(Integer.toString(value)) + "00 €");
                    break;
                case 1:
                    this.valueDialogKeyboard.setText(countInteger + "." + countDecimal.append(Integer.toString(value)) + "0 €");
                    break;
                case 2:
                    this.valueDialogKeyboard.setText(countInteger + "." + countDecimal.append(Integer.toString(value)) + " €");
                    break;
                case 3:
                    this.valueDialogKeyboard.setText(countInteger + "." + countDecimal.append(Integer.toString(value)) + " €");
                    break;
            }


        } else {

            if (countInteger.length() == 6){
                countInteger = new StringBuilder(countInteger.substring(0, 5));
            }

            if (countInteger.length() == 0 && value != 0) {
                countInteger.append(Integer.toString(value));

                this.valueDialogKeyboard.setText(countInteger + ".000 €");
            } else {
                if (value != 0 || countInteger.length() != 0) {
                    countInteger.append(Integer.toString(value));

                    this.valueDialogKeyboard.setText(countInteger + ".000 €");
                }
            }

        }

    }
}

