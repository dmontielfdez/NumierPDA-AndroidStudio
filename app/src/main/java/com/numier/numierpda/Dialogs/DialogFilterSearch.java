package com.numier.numierpda.Dialogs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.numier.numierpda.Models.Product;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;


public class DialogFilterSearch extends DialogFragment {
	
	
	private EditText campoBusqueda;
	private ListView listadoProductos;
	private List <Product> productosTemp;
	private List <String> cadenas;
	ArrayAdapter<String> adaptadorClasico;
	Product productoElegido;
	String numOrden;
	private Activity context;
	
	public DialogFilterSearch (List <Product> productos, String numOrden, Activity context){
		this.productosTemp = productos;
		this.numOrden = numOrden;
		cadenas = new ArrayList <String>();
		this.context = context;
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final Dialog dialogo = new Dialog(getActivity());

		dialogo.setCanceledOnTouchOutside(false);

		dialogo.setTitle(R.string.search_product);

		dialogo.getWindow().setGravity(Gravity.CENTER);
		
		View vista = getActivity().getLayoutInflater().inflate(R.layout.dialog_filter_product, null);
		
		campoBusqueda = (EditText) vista.findViewById(R.id.inputDialogFilterSearch);
		listadoProductos = (ListView) vista.findViewById(R.id.listFilterProducts);

		for (Product producto: productosTemp){
			this.cadenas.add(producto.getName()+" - "+ ConversionTools.getFormatPrice(producto.getRate1(), false));
		}
		
		adaptadorClasico = new ArrayAdapter <String>(getActivity(), android.R.layout.simple_list_item_1, this.cadenas);
		listadoProductos.setAdapter(adaptadorClasico);
		

		campoBusqueda.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence str, int start, int before, int count) {
				adaptadorClasico.getFilter().filter(str);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}
			
			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		
		
		listadoProductos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				String nombre = (String) listadoProductos.getItemAtPosition(position);

				for (Product producto: productosTemp){
					String name = producto.getName()+" - "+ConversionTools.getFormatPrice(producto.getRate1(), false);
					if (name.equals(nombre)) {
						productoElegido = producto;
					}
				}

//				IntakeUtils.generateIntake(getActivity(), productoElegido, 1, CashFragment.buttonMenus.getText().toString(), ((Cash)getActivity()).getCashFragment().getHeader().getRate(), true);

				getDialog().cancel();
				
			}
		});
		
		dialogo.setContentView(vista);

		dialogo.show();

		return dialogo;
	}
	
	

	
	
	
	
	

}
