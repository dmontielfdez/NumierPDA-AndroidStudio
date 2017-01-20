package com.numier.numierpda.Dialogs;

import java.util.List;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;
import com.numier.numierpda.Tools.IntakeTools;

public class DialogDiscount extends DialogFragment implements OnClickListener {

    private StringBuilder countInteger;
    public double discount;
    public double originalAmount, newAmount;

    private Button one, two, three, four, five, six, seven, eight, nine, zero, ce, ok, cancel, decimal;
    private TextView valueDialogKeyboard;
    private View v;

    public DialogDiscount() {
        this.discount = 0.0;
        this.originalAmount = 0.0;
        this.newAmount = 0.0;
        this.countInteger = new StringBuilder("");
    }

    public DialogDiscount(double oldAmount) {
        this.discount = 0.0;
        this.originalAmount = oldAmount;
        this.newAmount = oldAmount;
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

            d.setTitle(getString(R.string.specify_discount));
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


        valueDialogKeyboard.setText("0%");

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

            case R.id.buttonCeDialogKeyboard:
                valueDialogKeyboard.setText("0%");
                this.countInteger = new StringBuilder("");

                break;

            case R.id.buttonOkDialogKeyboard:
                if (countInteger.toString().equals("")) {
                    countInteger.append("0");
                }

                this.discount = Integer.parseInt(countInteger.toString());

                if (discount > 100) {
                    Snackbar.make(v, getString(R.string.fail_discount_100), Snackbar.LENGTH_LONG).show();
                } else {
                    applyDiscount(discount);
                }

                break;
            case R.id.buttonCancelDialogKeyboard:
                getDialog().cancel();
                break;
        }
    }


    public void applyDiscount(double discount) {

        List<Detalle> listDetails = Cash.getCashFragment().getHeader().getDetails();


        boolean hayDescuento = false;
        int posDescuento = 0;

        int index = 0;
        for (Detalle detail : listDetails) {
            if (detail.getIdProduct().equals("DDDDD")) {
                hayDescuento = true;
                posDescuento = index;
            }
            index++;
        }


        double amount = Cash.getCashFragment().getHeader().getAmount();

        if (hayDescuento) {
            amount = amount + Math.abs(Cash.getCashFragment().getHeader().getDetails().get(posDescuento).getAmount());
            Cash.getCashFragment().getHeader().getDetails().remove(posDescuento);
        }

        if (discount != 0 && listDetails.size() > 0) {
            amount = (amount * discount) / 100;

            amount = -amount;

            Product productDiscount = new Product("DDDDD", "** Total Dto. " + (int) discount + "% **");

            productDiscount.setDiscount(discount);
            productDiscount.setRate1(amount);
            productDiscount.setRate2(amount);
            productDiscount.setRate3(amount);
            productDiscount.setRate4(amount);

            IntakeTools.generateIntake(getActivity(), productDiscount, 1, "");
        } else {
            Cash.getTicketFragment().setAmount(ConversionTools.getFormatPrice(amount, false));
            Cash.getTicketFragment().getAdapterList().notifyDataSetChanged();

            if (hayDescuento) {
                Cash.getCashFragment().setTextLastItem(getString(R.string.discount_delete));
            }
        }

        getDialog().cancel();
    }


    private void setNewValue(int value) {
        if (countInteger.length() == 3) {
            countInteger = new StringBuilder(countInteger.substring(0, 2));
        }

        countInteger.append(Integer.toString(value));

        valueDialogKeyboard.setText(countInteger.toString() + "%");

    }
}
