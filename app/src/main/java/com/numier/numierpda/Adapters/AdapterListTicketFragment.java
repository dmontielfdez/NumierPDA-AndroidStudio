package com.numier.numierpda.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.ProductCrud;
import com.numier.numierpda.Models.Detalle;
import com.numier.numierpda.Models.Modifier;
import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListTicketFragment extends ArrayAdapter<Detalle> {

    private Activity activity;
    private List<Detalle> details;
    private TextView valueProductName, valueUds, valuePrice, valueAmount, numOrden, listModifiers, messageTicket;
    private ImageView imagenImpresora;
    private LinearLayout row;
    private View lineSeparator;


    public AdapterListTicketFragment(Activity context, List<Detalle> listaConsumiciones) {
        super(context, R.layout.item_list_ticket, listaConsumiciones);
        this.activity = context;
        this.details = listaConsumiciones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = activity.getLayoutInflater().inflate(R.layout.item_list_ticket, null);

        Database db = new Database(activity);

        row = (LinearLayout) vista.findViewById(R.id.rowTicket);
        valueProductName = (TextView) vista.findViewById(R.id.inputProductTicket);
        valueUds = (TextView) vista.findViewById(R.id.inputUdsTicket);
        valuePrice = (TextView) vista.findViewById(R.id.inputPriceTicket);
        valueAmount = (TextView) vista.findViewById(R.id.inputAmountTicket);
        numOrden = (TextView) vista.findViewById(R.id.inputNumOrdenTicket);
        listModifiers = (TextView) vista.findViewById(R.id.listItemModifiers);
        messageTicket = (TextView) vista.findViewById(R.id.messageTicket);
        imagenImpresora = (ImageView) vista.findViewById(R.id.printerOkTicket);
        lineSeparator = (View) vista.findViewById(R.id.lineSeparatorTicket);

        // Table estilo zebra

        if (position % 2 == 0) {
            row.setBackgroundResource(R.drawable.selector_item_ticket_par);
        } else {
            row.setBackgroundResource(R.drawable.selector_item_ticket_impar);
        }

        if (details.get(position).getIdProduct().equals("DDDDD")) {
            valueProductName.setText(details.get(position).getProductName());
            valueUds.setVisibility(View.INVISIBLE);
            imagenImpresora.setVisibility(View.INVISIBLE);
            valuePrice.setVisibility(View.INVISIBLE);
            row.setBackgroundResource(R.drawable.selector_item_ticket_discount);
            valueAmount.setText(ConversionTools.getFormatPrice(details.get(position).getAmount(), false));
        } else if (details.get(position).getIdProduct().equals("*****")) {
            valueAmount.setVisibility(View.GONE);
            imagenImpresora.setVisibility(View.GONE);
            valuePrice.setVisibility(View.GONE);
            valueUds.setVisibility(View.GONE);
            valueProductName.setVisibility(View.GONE);
            lineSeparator.setVisibility(View.VISIBLE);
        } else if(details.get(position).getIdProduct().equals("ZZZZZ")){
            valueProductName.setText(details.get(position).getTitle());
            valueUds.setText(ConversionTools.getFormatUds(details.get(position).getQuantity(), false));
            valuePrice.setText(ConversionTools.getFormatPrice(details.get(position).getPrice(), false));
            valueAmount.setText(ConversionTools.getFormatPrice(details.get(position).getAmount(), false));
        } else {

            this.valueProductName.setText(details.get(position).getTitle());

            String numOrden = "";
            if (!details.get(position).getNumOrden().equals("")) {
                numOrden = details.get(position).getNumOrden() + " ";
            }

            if (!numOrden.equals("")) {
                this.numOrden.setText(numOrden);
                this.numOrden.setVisibility(View.VISIBLE);
            }

            // Unidades o cantidad
            Product p = new ProductCrud(db).findById(details.get(position).getIdProduct());
            if(p.getAskWeight() == 0){
                valueUds.setText(ConversionTools.getFormatUds(details.get(position).getQuantity(), details.get(position).isDecimalQuantity()));
            } else{
                valueUds.setText(ConversionTools.getFormatKg(details.get(position).getQuantity(), true));
            }

            // Precio
            valuePrice.setText(ConversionTools.getFormatPrice(details.get(position).getPrice(), false));

            // Importe o gasto del detalle
            valueAmount.setText(ConversionTools.getFormatPrice(details.get(position).getAmount(), false));

            // Imagen impresion
            if (details.get(position).getPrinted() == 0) {
                imagenImpresora.setVisibility(View.INVISIBLE);
            }

            if(!details.get(position).getOption().equals("")){
                messageTicket.setText(details.get(position).getOption());
                messageTicket.setVisibility(View.VISIBLE);
            }

            if (details.get(position).getListModifier() != null) {
                ArrayList<Modifier> list = (ArrayList<Modifier>) details.get(position).getListModifier();
                String modifiers = "";
                for (Modifier modifier : list) {

                    String var = "";
                    if (modifier.getPrice() > 0) {
                        var = " (+ " + ConversionTools.getFormatPrice(modifier.getPrice(), false) + ")";
                    } else {
                        var = " (" + ConversionTools.getFormatPrice(modifier.getPrice(), false) + ")";
                    }


                    modifiers += " - " + modifier.getName() + var + "\n";
                }

                listModifiers.setText(modifiers);
                if (!modifiers.equals("")) {
                    listModifiers.setVisibility(View.VISIBLE);
                }

            }


            if (details.get(position).isNoImprimir()) {
                valueProductName.setTextColor(Color.GRAY);
                valueAmount.setTextColor(Color.GRAY);
                valuePrice.setTextColor(Color.GRAY);
                valueUds.setTextColor(Color.GRAY);
            }

        }

        return vista;
    }
}
