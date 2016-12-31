package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Models.SubproductSeling;

public class SelingSubproductCrud {

	// Atributos
	private Database db;

	// Constructor
	public SelingSubproductCrud(Database db) {
		this.db = db;
	}

	public boolean insert(List<SubproductSeling> listObjects) {
		try {
			for (SubproductSeling c : listObjects) {
				ContentValues values = new ContentValues();
				values.put("ID_PRODUCT", c.getIdProduct());
				values.put("ID_SUBPRODUCT", c.getIdSubproduct());
				values.put("TYPE", c.getType());
				values.put("CHECKED", c.isChecked());
				values.put("COUNTER", c.getCounter());
				values.put("NAME", c.getName());
				values.put("PRICE", c.getPrice());

				db.getWritableDatabase().insert("SELING_SUBPRODUCT", null, values);

			}
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}

	public void deleteAll() {

		db.getWritableDatabase().execSQL("DELETE FROM SELING_SUBPRODUCT");

	}

	public void deleteSeling(String idProduct) {

		db.getWritableDatabase().execSQL("DELETE FROM SELING_SUBPRODUCT WHERE ID_PRODUCT = '"+idProduct+"'");

	}

	public void setChecked(String idProduct, String idSubproduct, int checked) {

		db.getWritableDatabase().execSQL("UPDATE SELING_SUBPRODUCT SET CHECKED = "+checked+" WHERE ID_SUBPRODUCT = '"+idSubproduct+"' AND ID_PRODUCT = '"+idProduct+"'");

	}

	public void setCheckedOpcional(String idProduct, String idSubproduct, int checked) {

		db.getWritableDatabase().execSQL("UPDATE SELING_SUBPRODUCT SET CHECKED = 0 WHERE TYPE = 'O' AND ID_PRODUCT = '"+idProduct+"'");
		db.getWritableDatabase().execSQL("UPDATE SELING_SUBPRODUCT SET CHECKED = "+checked+" WHERE ID_SUBPRODUCT = '"+idSubproduct+"' AND ID_PRODUCT = '"+idProduct+"'");
	}

	public List<SubproductSeling> getAllSeling(String idProduct) {

		List<SubproductSeling> subproducts = new ArrayList<SubproductSeling>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM SELING_SUBPRODUCT WHERE ID_PRODUCT = '"+idProduct+"' order by TYPE",
				null);
		
		if (c.moveToFirst()) {
			do {
				boolean checked = false;
				if(c.getInt(4) == 1){
					checked = true;
				}
				subproducts.add(new SubproductSeling(
						c.getString(0),
						c.getString(1),
						c.getString(2),
						c.getString(3),
						checked,
						c.getInt(5),
						c.getDouble(6)));
			} while (c.moveToNext());
		}
		c.close();


		db.close();


		return subproducts;
	}

	public List<SubproductSeling> getSeling(String idProduct, String type) {

		List<SubproductSeling> subproducts = new ArrayList<SubproductSeling>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM SELING_SUBPRODUCT WHERE ID_PRODUCT = '"+idProduct+"' AND TYPE='"+type+"'",
				null);

		if(c.getCount() == 0){
			c = db.getReadableDatabase().rawQuery("SELECT * FROM PRODUCTSUBPRODUCT WHERE ID_PRODUCT = '"+idProduct+"' AND TYPE='"+type+"'",
					null);

			if (c.moveToFirst()) {
				do {
					Cursor cu = db.getReadableDatabase().rawQuery("SELECT NAME FROM SUBPRODUCT WHERE ID_SUBPRODUCT = '"+c.getString(0)+"'",
							null);
					String name = "";
					if (cu.moveToFirst()) {
						do {				
							name = cu.getString(0);
						} while (cu.moveToNext());
					}

					cu.close();

					boolean checked = false;
					if(type.equals("B")){
						checked = true;
					}

					subproducts.add(new SubproductSeling(
							idProduct,
							c.getString(0),
							name,
							type,
							checked,
							0,
							c.getDouble(3)));
				} while (c.moveToNext());
			}
			c.close();

			insert(subproducts);
		} else{
			c = db.getReadableDatabase().rawQuery("SELECT * FROM SELING_SUBPRODUCT WHERE ID_PRODUCT = '"+idProduct+"' AND TYPE='"+type+"'",
					null);

			if (c.moveToFirst()) {
				do {
					boolean checked = false;
					if(c.getInt(4) == 1){
						checked = true;
					}
					subproducts.add(new SubproductSeling(
							c.getString(0),
							c.getString(1),
							c.getString(2),
							c.getString(3),
							checked,
							c.getInt(5),
							c.getDouble(6)));
				} while (c.moveToNext());
			}
			c.close();
		}

		db.close();

		Collections.sort(subproducts, new Comparator<SubproductSeling>() {
			@Override
			public int compare(SubproductSeling s1, SubproductSeling s2) {
				int f = s1.getName().compareTo(s2.getName());
				return (f != 0) ? f : s1.getName().compareTo(s2.getName());
			}
		});

		return subproducts;
	}

	public String getCounter(String idProduct, String idSubproduct) {

		String counter = "";

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM SELING_SUBPRODUCT WHERE ID_PRODUCT ='" + idProduct+"' AND ID_SUBPRODUCT = '"+idSubproduct+"'",
				null);

		try {
			if (c.moveToFirst()) {
				counter = Integer.toString(c.getInt(5));

			}
			c.close();

			db.close();

			return counter;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "0";

	}

	public void setCounter(int counter, String idProduct, String idSubproduct) {

		db.getWritableDatabase().execSQL("UPDATE SELING_SUBPRODUCT set counter = "+counter+" WHERE ID_PRODUCT = '"+idProduct+"' AND ID_SUBPRODUCT = '"+idSubproduct+"'");

	}


	public List<SubproductSeling> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
