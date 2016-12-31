package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Models.MenuDet;


public class MenuDetCrud {

	// Atributos
	private Database db;

	// Constructor
	public MenuDetCrud(Database db) {
		this.db = db;
	}

	// Inserción de datos
	public boolean insert(List<MenuDet> listObjects) {

		try {
			for (MenuDet c : listObjects) {
				ContentValues values = new ContentValues();
				values.put("ID_MENU", c.getIdMenu());
				values.put("CODE", c.getCode());
				values.put("NUM_PLATO", c.getNumPlato());
				values.put("COUNTER", c.getCounter());

				db.getWritableDatabase().insert("MENU_DET", null, values);

			}
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}

	public List<MenuDet> getAll() {

		List<MenuDet> menudets = new ArrayList<MenuDet>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU_DET",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				menudets.add(new MenuDet(c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getInt(3),
						c.getInt(4)));
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return menudets;
	}

	public List<MenuDet> findByMenu(String idMenu, int numPlato) {

		List<MenuDet> menudets = new ArrayList<MenuDet>();


		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU_DET WHERE ID_MENU = '"+idMenu+"' and NUM_PLATO = "+numPlato,
				null);


		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				menudets.add(new MenuDet(c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getInt(3),
						c.getInt(4)));
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return menudets;
	}

	public List<MenuDet> findByPlato(String idMenu, int numPlato) {

		List<MenuDet> menudets = new ArrayList<MenuDet>();


		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU_DET WHERE ID_MENU = '"+idMenu+"' AND NUM_PLATO="+numPlato,
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				menudets.add(new MenuDet(c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getInt(3),
						c.getInt(4)));
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return menudets;
	}

	public String getCounter(int id) {

		String counter = "";

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU_DET WHERE ID_MENU_DET =" + id,
				null);

		try {
			if (c.moveToFirst()) {
				counter = Integer.toString(c.getInt(4));

			}
			c.close();

			db.close();

			return counter;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "0";

	}

	public int getTotalCounter(int numPlato, String idMenu) {

		int counter = 0;

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU_DET WHERE NUM_PLATO =" + numPlato+" AND ID_MENU = '"+idMenu+"'",
				null);

		try {
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					counter += c.getInt(4);
				} while (c.moveToNext());
			}
			c.close();

			db.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return counter;
	}

	public void setCounter(int counter, int id) {

		db.getWritableDatabase().execSQL("UPDATE MENU_DET set counter = "+counter+" WHERE ID_MENU_DET = "+id);

	}

	public void resetCounter() {
		db.getWritableDatabase().execSQL("UPDATE MENU_DET set counter = 0");
	}

	public ArrayList<MenuDet> checkMenu(String id) {

		ArrayList<MenuDet> list = new ArrayList<MenuDet>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU_DET WHERE ID_MENU = '"+id+"' AND COUNTER > 0 ORDER BY NUM_PLATO",null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {

				list.add(new MenuDet(c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getInt(3),
						c.getInt(4)));

			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return list;

	}
}
