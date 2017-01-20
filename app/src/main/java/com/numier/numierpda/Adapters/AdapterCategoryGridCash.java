package com.numier.numierpda.Adapters;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.numier.numierpda.Models.Category;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.PreferencesTools;


public class AdapterCategoryGridCash extends ArrayAdapter<Category> {

	private Activity activity;
	private List<Category> categorias;

	private TextView textoItem;

	public AdapterCategoryGridCash(Activity context, List<Category> listaCategorias) {
		super(context, R.layout.item_grid_cash_category, listaCategorias);
		this.activity = context;
		this.categorias = listaCategorias;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vista = this.activity.getLayoutInflater().inflate(R.layout.item_grid_cash_category, null);

		this.textoItem = (TextView) vista.findViewById(R.id.nombreCategoria);
		this.textoItem.setText(this.categorias.get(position).getName());

		// Pongo el tamaño de texto que esta definido en los settings
		String textSize = PreferencesTools.getValueOfPreferences(activity, "textSize");
		if(!textSize.equals("")){
			textoItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(textSize));

		}

		return vista;
	}

}
