package com.numier.numierpda.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Fragments.CashFragment;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.DialogsTools;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.ArrayList;
import java.util.List;

public class DialogOptionItem extends DialogFragment {

    private List<Detalle> details;
    private int position;

    public DialogOptionItem(List<Detalle> details, int position) {
        this.details = details;
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.select_option));

        final Detalle detalle = details.get(position);

        String[] opciones;
        if (detalle.getIdProduct().equals("*****")) {
            opciones = new String[]{getString(R.string.delete_line)};
        } else {
            opciones = new String[]{getString(R.string.delete_line), getString(R.string.modify_uds), getString(R.string.modify_price), getString(R.string.modify_num_plato), getString(R.string.message_to_printer), getString(R.string.select_modifiers), getString(R.string.see_description)};
        }

        builder.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, opciones),

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Database db = new Database(getActivity());

                        Product p = new ProductCrud(db).findById(detalle.getIdProduct());

                        Detalle detalle = details.get(position);

                        final String permitirCambios = PreferencesTools.getValueOfPreferences(getActivity(), "permitirCambios");


                        switch (which) {
                            case 0:

                                if (permitirCambios.equals("S")) {
                                    new DialogDeleteItem(details, position).show(getActivity().getSupportFragmentManager(), "dialog");
                                } else {
                                    DialogsTools.launchCustomDialog(getActivity(), getString(R.string.info_not_authorized_delete_item));
                                }


                                break;
                            case 1:

                                if (permitirCambios.equals("S")) {
                                    if (detalle.getIdProduct().equals("ZZZZZ")) {
                                        new DialogModifyUds(detalle, position).show(getActivity().getSupportFragmentManager(), "dialog");
                                    } else {
                                        if (p.getAskWeight() == 0) {
                                            new DialogModifyUds(detalle, position).show(getActivity().getSupportFragmentManager(), "dialog");
                                        } else {
                                            new DialogModifyWeight(detalle, position).show(getActivity().getSupportFragmentManager(), "dialog");
                                        }
                                    }

                                } else {
                                    DialogsTools.launchCustomDialog(getActivity(), getString(R.string.info_not_authorized_modify_item));
                                }


                                break;

                            case 2:
                                new DialogModifyPrice(detalle, position).show(getActivity().getSupportFragmentManager(), "dialog");

                                break;

                            case 3:
                                new DialogModifyNumPlato(detalle, position).show(getActivity().getSupportFragmentManager(), "dialog");
                                break;
                            case 4:
                                List<String> messages = new ArrayList<String>();

                                if (detalle.getIdProduct() != "ZZZZZ") {
                                    Product product = new ProductCrud(db).findById(detalle.getIdProduct());

                                    db.close();

                                    if (product.getOption1().length() > 0) {
                                        messages.add(product.getOption1());
                                    }
                                    if (product.getOption2().length() > 0) {
                                        messages.add(product.getOption2());
                                    }
                                    if (product.getOption3().length() > 0) {
                                        messages.add(product.getOption3());
                                    }
                                    if (product.getOption4().length() > 0) {
                                        messages.add(product.getOption4());
                                    }
                                    if (product.getOption5().length() > 0) {
                                        messages.add(product.getOption5());
                                    }

                                }

                                new DialogModifyMessage(messages.toArray(new String[messages.size()]), detalle, position).show(getActivity().getSupportFragmentManager(), "Dialogo");

                                break;

                            case 5:
                                if (detalle.getIdProduct() != "ZZZZZ") {
                                    new DialogModifiers(getActivity(), details, position).show(getActivity().getSupportFragmentManager(), "dialogModifier");
                                }
                                break;

                            case 6:
                                if (detalle.getIdProduct() != "ZZZZZ") {
                                    new DialogDescri(getActivity(), details, position).show(getActivity().getSupportFragmentManager(), "dialogDescri");
                                    break;
                                }
                                break;


                        }
                    }
                });

        return builder.create();
    }
}
