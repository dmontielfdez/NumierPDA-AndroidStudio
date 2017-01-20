package com.numier.numierpda.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.numier.numierpda.Activities.Cash;
import com.numier.numierpda.Adapters.AdapterListTicketFragment;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Dialogs.DialogDeleteDiscount;
import com.numier.numierpda.Dialogs.DialogOptionItem;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.List;


public class TicketFragment extends ListFragment {
    public AdapterListTicketFragment adapterList;
    private List<Detalle> details;
    private Vibrator vi;
    private Button cancelAccount, cashAccount;
    private TextView amount;


    public TicketFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        details = Cash.getCashFragment().getHeader().getDetails();
        adapterList = new AdapterListTicketFragment(getActivity(), details);

        vi = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        cashAccount = (Button) view.findViewById(R.id.cashAccount);
        cancelAccount = (Button) view.findViewById(R.id.cancelAccount);
        amount = (TextView) view.findViewById(R.id.billAmount);

        LinearLayout layoutButtonTicket = (LinearLayout) view.findViewById(R.id.layoutButtonsTicket);

        String displayCash = PreferencesTools.getValueOfPreferences(getActivity(), "displayCash");
        String voidButton = PreferencesTools.getValueOfPreferences(getActivity(), "voidButton");

        if (voidButton.equals("S")) {
            layoutButtonTicket.setVisibility(View.VISIBLE);
            cancelAccount.setVisibility(View.VISIBLE);

            cancelAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vi.vibrate(50);
                    Cash.getCashFragment().cancelAccount();
                }
            });
        }

        if (displayCash.equals("S")) {
            layoutButtonTicket.setVisibility(View.VISIBLE);
            cashAccount.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void addAndUpdate(Detalle detail) {

        Database db = new Database(getActivity());

        boolean add = false;

        // Si el detalle es una linea de separacion no lo agrupo
        Log.d("idProduct", detail.getIdProduct());
        if (!detail.getIdProduct().equals("*****")) {
            // Si el detalle es un articulo no definido no lo agrupo
            if (!detail.getIdProduct().equals("ZZZZZ")) {
                // Si el detalle es un descuento no lo agrupo
                if (!detail.getIdProduct().equals("DDDDD")) {
                    // Si el detalles es un producto al peso no lo agrupo
                    if (new ProductCrud(db).findById(detail.getIdProduct()).getAskWeight() == 0) {

                        for (int i = 0; i < details.size(); i++) {

                            // Compruebo si el id es el mismo
                            if (details.get(i).getIdProduct().equals(detail.getIdProduct())) {

                                // Compruebo si está imprimido o no
                                if (details.get(i).getPrinted() == 0) {

                                    // Compruebo que tengan el mismo precio
                                    if (details.get(i).getPrice() == detail.getPrice()) {

                                        if (details.get(i).getProductName().equals(detail.getProductName())) {
                                            // Compruebo que tenga la misma opcion
                                            if (details.get(i).getOption().equals(detail.getOption())) {

                                                if (details.get(i).getNumOrden().equals(detail.getNumOrden())) {

                                                    if (details.get(i).getListSubProducts().toString().equals(detail.getListSubProducts().toString())) {

                                                        if (details.get(i).getListModifier().toString().equals(detail.getListModifier().toString())) {

                                                            if (details.get(i).getListSubProductsExtra().toString().equals(detail.getListSubProductsExtra().toString())) {

                                                                if (details.get(i).getListSubProductsOpcional().toString().equals(detail.getListSubProductsOpcional().toString())) {
                                                                    details.get(i).setQuantity(details.get(i).getQuantity() + detail.getQuantity());
                                                                    details.get(i).setAmount(details.get(i).getAmount() + detail.getAmount());
                                                                    add = true;
                                                                    detail = details.get(i);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if (!add) {
            details.add(0, detail);
        }

        adapterList.notifyDataSetChanged();

        Cash.getCashFragment().setDetailsToHeader(details, detail);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        vi.vibrate(50);

        if (this.details.get(position).getIdProduct().equals("DDDDD") == true) {
            new DialogDeleteDiscount(this.details, position).show(getActivity().getSupportFragmentManager(), "dialog");
        } else {
            DialogOptionItem dialog = new DialogOptionItem(this.details, position);

            dialog.show(getActivity().getSupportFragmentManager(), "dialog");
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(adapterList);
    }


    public void setAmount(String amount) {
        this.amount.setText(amount);
    }

    public AdapterListTicketFragment getAdapterList() {
        return adapterList;
    }

    public List<Detalle> getDetails() {
        return details;
    }

    public void setDetails(List<Detalle> details) {
        this.details = details;
    }

    public void setAdapterList(AdapterListTicketFragment adapterList) {
        this.adapterList = adapterList;
    }


}
