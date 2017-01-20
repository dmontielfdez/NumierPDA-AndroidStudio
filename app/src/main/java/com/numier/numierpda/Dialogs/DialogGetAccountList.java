package com.numier.numierpda.Dialogs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.numier.numierpda.Adapters.AdapterGridViewGetAccountList;
import com.numier.numierpda.Controllers.GetAccount;
import com.numier.numierpda.Models.Header;
import com.numier.numierpda.R;

import java.util.List;

public class DialogGetAccountList extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    // Atributos
    private List<Header> listHeaders;

    // View
    private GridView gridview;
    private Button back;


    // Enviamos la opcion que queramos: 1 - recuperar cuenta, 2 - mandar mensaje
    private int option;

    public DialogGetAccountList(List<Header> listHeaders, int option) {
        this.listHeaders = listHeaders;
        this.option = option;
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

            d.setTitle(getString(R.string.get_account));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_get_account_list, container, false);

        gridview = (GridView) v.findViewById(R.id.gridViewDialogGetBill);
        back = (Button) v.findViewById(R.id.buttonCancelDialogGetAccountList);

        this.back.setOnClickListener(this);
        this.gridview.setOnItemClickListener(this);

        AdapterGridViewGetAccountList adapter = new AdapterGridViewGetAccountList(getActivity(), listHeaders);

        gridview.setAdapter(adapter);

        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonCancelDialogGetAccountList:
                this.dismiss();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(option==1) {
            this.dismiss();
            new GetAccount(getActivity(), 0).execute(listHeaders.get(position));
        }
//        } else if(option == 2){
//            showPrinters(listHeaders.get(position), fragmentActivity);
//        } else if(option == 4){
//            ((Cash) getActivity()).getCashFragment().launchSaveBill(5);
//        }



    }
}
