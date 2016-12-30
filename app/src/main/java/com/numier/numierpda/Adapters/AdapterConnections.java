package com.numier.numierpda.Adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.numier.numierpda.Models.Connection;
import com.numier.numierpda.R;

public class AdapterConnections extends ArrayAdapter<Connection>{

	private LayoutInflater layoutInflater;
	private Context context;

	public AdapterConnections(Context context, ArrayList<Connection> objects) {
		super(context, 0, objects);
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();

			convertView = layoutInflater.inflate(R.layout.item_list_connection, parent, false);

			holder.setName((TextView) convertView.findViewById(R.id.name_connection));
			holder.setUrl((TextView) convertView.findViewById(R.id.url_connection));
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		final Connection row = getItem(position);


		holder.getName().setText(row.getName());
		holder.getUrl().setText(row.getUrl());

		return convertView;
	}

	static class Holder {
		TextView name, url;

		public TextView getName() {
			return name;
		}

		public void setName(TextView name) {
			this.name = name;
		}

		public TextView getUrl() {
			return url;
		}

		public void setUrl(TextView url) {
			this.url = url;
		}


	}

	
	

}
