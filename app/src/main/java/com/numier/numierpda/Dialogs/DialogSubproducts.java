package com.numier.numierpda.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import java.util.List;


public class DialogSubproducts extends DialogFragment implements View.OnClickListener {

    private List<Subproduct> subProducts;
    List<Subproduct> subProductsRequired;
    ListView listView;
    private Product product;
    private Detalle detail;
    private String[] subProductsName;
    private Button cancel;

    public DialogSubproducts(List<Subproduct> subProducts, Product product, Detalle detail, List<Subproduct> subProductsRequired) {
        this.subProducts = subProducts;
        this.product = product;
        this.detail = detail;
        this.subProductsRequired = subProductsRequired;
        subProductsName = new String[subProducts.size()];

        for (int i = 0; i < subProducts.size(); i++) {
            subProductsName[i] = subProducts.get(i).getName();
        }

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

            d.setTitle(getString(R.string.select_num_subproduct) + " " + (detail.getListSubProducts().size() + 1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_subproducts, container, false);

        cancel = (Button) v.findViewById(R.id.buttonCancelDialogSubproducts);
        this.cancel.setOnClickListener(this);

        listView = (ListView) v.findViewById(R.id.listviewSubproducts);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, subProductsName);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                detail.getListSubProducts().add(subProducts.get(pos));

                if (product.getAbsolutPrice() == 0) {
                    if(product.getAskPrice() == 0){
                        detail.setPriceExtraSubproduct(detail.getPriceExtraSubproduct() + subProducts.get(pos).getPrice());
                    }
                }

                // Compruebo si tengo tantos subproducto como repeticiones me exige el producto
                if (detail.getListSubProducts().size() == product.getNumberSubproducts()) {

                    for (Subproduct value : subProductsRequired) {
                        detail.getListSubProducts().add(value);
                    }

                    getDialog().cancel();
                    IntakeTools.launchDialogPrice(product, detail);


                } else {
                    getDialog().cancel();
                    new DialogSubproducts(subProducts, product, detail, subProductsRequired).show(getActivity().getSupportFragmentManager(), "dial");
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCancelDialogSubproducts:
                getDialog().cancel();
                break;
        }
    }
}