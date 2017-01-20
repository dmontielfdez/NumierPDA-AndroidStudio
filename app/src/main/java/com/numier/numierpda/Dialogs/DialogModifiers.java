package com.numier.numierpda.Dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Adapters.AdapterModifiers;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ModifierCrud;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Modifier;
import com.numier.numierpda.R;

import java.util.ArrayList;
import java.util.List;

public class DialogModifiers extends DialogFragment implements View.OnClickListener {


    ListView listview;
    AdapterModifiers adapter;
    ArrayList<Modifier> listModifiers;
    private Database db;
    private Activity context;
    private List<Detalle> details;
    private int position;
    Button acceptModifier;


    public DialogModifiers(Activity context, List<Detalle> details, int position) {
        this.context = context;
        this.details = details;
        this.position = position;
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

            d.setTitle(getString(R.string.select_modifiers));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_modifier, container, false);

        db = new Database(context);

        acceptModifier = (Button) v.findViewById(R.id.buttonAcceptModifier);
        acceptModifier.setOnClickListener(this);

        listview = (ListView) v.findViewById(R.id.listModifiers);

        String grupo = new ProductCrud(db).findById(details.get(position).getIdProduct()).getCategory();
        listModifiers = (ArrayList<Modifier>) new ModifierCrud(db).getAllGrupo(grupo);

        setModifiers((ArrayList<Modifier>) details.get(position).getListModifier());

        adapter = new AdapterModifiers(context, listModifiers);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Modifier mod = listModifiers.get(position);

                if (mod.isChecked()) {
                    mod.setChecked(false);
                } else {
                    mod.setChecked(true);
                }

                adapter.notifyDataSetChanged();
            }
        });

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAcceptModifier:

                ProductCrud crud = new ProductCrud(db);
                Double price = crud.getPriceProduct(details.get(position).getIdProduct(), details.get(position).getRate());
                details.get(position).setPrice(price);

                double incrementoDinero = 0;

                ArrayList<Modifier> listMo = new ArrayList<Modifier>();
                for (Modifier modifier : listModifiers) {
                    if (modifier.isChecked()) {
                        incrementoDinero += modifier.getPrice();
                        listMo.add(modifier);
                    }
                }

                details.get(position).setListModifier(listMo);
                details.get(position).setPrice(details.get(position).getPrice() + incrementoDinero);
                details.get(position).setAmount(details.get(position).getQuantity() * details.get(position).getPrice());

                // Modifico la lista
                Cash.getTicketFragment().getAdapterList().notifyDataSetChanged();

                Cash.getCashFragment().setDetailsToHeader(details, details.get(0));

                getDialog().cancel();
                break;

            default:
                break;
        }
    }

    public void setModifiers(ArrayList<Modifier> listDetail) {
        if (listDetail != null) {
            for (Modifier modifier : listModifiers) {
                for (Modifier modifier2 : listDetail) {
                    if (modifier.getId() == modifier2.getId()) {
                        if (modifier.isChecked() != modifier2.isChecked()) {
                            modifier.setChecked(true);
                        }
                    }
                }
            }
        }
    }
}
