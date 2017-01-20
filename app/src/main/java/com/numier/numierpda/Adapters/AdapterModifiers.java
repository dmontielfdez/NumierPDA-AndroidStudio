package com.numier.numierpda.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.numier.numierpda.Models.Modifier;
import com.numier.numierpda.R;
import com.numier.numierpda.Tools.ConversionTools;

import java.util.ArrayList;


public class AdapterModifiers extends ArrayAdapter<Modifier> implements View.OnClickListener {

    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterModifiers(Context context, ArrayList<Modifier> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.item_list_modifier, parent, false);

            holder.setTextViewTitle((TextView) convertView.findViewById(R.id.name_modifier));
            holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkBox_modifier));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Modifier row = getItem(position);

        String var = "";
        if(row.getPrice() != 0){
            if(row.getPrice() > 0){
                var = " (+ "+ ConversionTools.getFormatPrice(row.getPrice(), false)+")";
            } else{
                var = " ("+ConversionTools.getFormatPrice(row.getPrice(), false)+")";
            }

        }

        holder.getTextViewTitle().setText(row.getName()+var);
        holder.getCheckBox().setTag(position);
        holder.getCheckBox().setChecked(row.isChecked());
        holder.getCheckBox().setOnClickListener(this);

        changeBackground(holder.getCheckBox(), row.getGrupo());

        return convertView;
    }

    static class Holder {
        TextView textViewTitle;
        CheckBox checkBox;

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle) {
            this.textViewTitle = textViewTitle;
        }

        public CheckBox getCheckBox()  {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox)  {
            this.checkBox = checkBox;
        }

    }

    @Override
    public void onClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position = (Integer) v.getTag();


        changeBackground(checkBox, getItem(position).getGrupo());
        getItem(position).setChecked(checkBox.isChecked());

    }

    private void changeBackground(CheckBox checkBox, String grupo) {
        View row = (View) checkBox.getParent();
        int selector;
        if (checkBox.isChecked()) {
            selector = R.drawable.selector_item_modifier_checked;
        } else {
            if(grupo.equals("")){
                selector = R.drawable.selector_item_modifier_general;
            } else{
                selector = R.drawable.selector_item_modifier_specific;
            }
        }
        row.setBackgroundResource(selector);
    }
}
