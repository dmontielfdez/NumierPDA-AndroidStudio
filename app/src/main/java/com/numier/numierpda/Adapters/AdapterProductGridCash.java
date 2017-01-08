package com.numier.numierpda.Adapters;


import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;

import java.util.List;


public class AdapterProductGridCash extends ArrayAdapter<Product> {

    // Atributos
    private Activity activity;
    private List<Product> products;
    private RelativeLayout itemLayout;

    // View
    private TextView textoItem;

    // Constructor
    public AdapterProductGridCash(Activity context, List<Product> productsList) {
        super(context, R.layout.item_grid_cash_product, productsList);
        this.activity = context;
        this.products = productsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = this.activity.getLayoutInflater().inflate(R.layout.item_grid_cash_product, null);

        this.textoItem = (TextView) vista.findViewById(R.id.nombreCategoria);
        this.itemLayout = (RelativeLayout) vista.findViewById(R.id.itemGridLayout);

        this.textoItem.setText(this.products.get(position).getName());

        this.itemLayout.setBackgroundResource(findColorByString(this.products.get(position).getColor()));

        String textSize = PreferencesTools.getValueOfPreferences(activity, "textSize");
        textoItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(textSize));

        return vista;
    }

    public static int findColorByString(String colorName) {

        if (colorName.equalsIgnoreCase("blanco")) {
            return R.drawable.selector_product_cash_white;
        } else if (colorName.equalsIgnoreCase("amarillo")) {
            return R.drawable.selector_product_cash_yellow;
        } else if (colorName.equalsIgnoreCase("rojo")) {
            return R.drawable.selector_product_cash_red;
        } else if (colorName.equalsIgnoreCase("azul")) {
            return R.drawable.selector_product_cash_blue;
        } else if (colorName.equalsIgnoreCase("morado")) {
            return R.drawable.selector_product_cash_purple;
        } else if (colorName.equalsIgnoreCase("verde")) {
            return R.drawable.selector_product_cash_green;
        } else {
            return R.drawable.selector_product_cash_white;
        }
    }

}
