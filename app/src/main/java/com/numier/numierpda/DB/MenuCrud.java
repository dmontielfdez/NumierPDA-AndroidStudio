package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Models.Menu;
import com.numier.numierpda.Models.MenuDet;
import com.numier.numierpda.Models.MenuItem;


public class MenuCrud implements Crud<Menu> {

	// Atributos
	private Database db;

	// Constructor
	public MenuCrud(Database db) {
		this.db = db;
	}

	public boolean insert(Menu c) {
		try {

			ContentValues values = new ContentValues();
			values.put("CODE_MENU", c.getIdProduct());
			values.put("TITLE1", c.getTitle1());
			values.put("TITLE2", c.getTitle2());
			values.put("TITLE3", c.getTitle3());
			values.put("TITLE4", c.getTitle4());
			values.put("TITLE5", c.getTitle5());
			values.put("TITLE6", c.getTitle6());

			db.getWritableDatabase().insert("MENU", null, values);

		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Menu> getAll() {

		List<Menu> menus = new ArrayList<Menu>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				menus.add(new Menu(c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getString(3),
						c.getString(4),
						c.getString(5),
						c.getString(6),
						c.getString(7)
						));
			} while (c.moveToNext());
		}
		c.close();

		return menus;
	}

	public List<MenuItem> findByIdProduct(int idProduct) {

		List<MenuItem> menus = new ArrayList<MenuItem>();
		String[] titles = new String[6];

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU WHERE CODE_MENU = '"+idProduct+"'",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				titles[0] = c.getString(2);
				titles[1] = c.getString(3);
				titles[2] = c.getString(4);
				titles[3] = c.getString(5);
				titles[4] = c.getString(6);
				titles[5] = c.getString(7);
			} while (c.moveToNext());
		}
		c.close();

		for (int i = 0; i < titles.length; i++) {
			ArrayList<MenuDet> listMenuDets = (ArrayList<MenuDet>) new MenuDetCrud(db).findByMenu(Integer.toString(idProduct), i);

			if(listMenuDets.size() != 0){
				menus.add(new MenuItem(titles[i], listMenuDets));
			} 
		}

		return menus;
	}

	public int getNumTitlesMenu(String idProduct) {

		List<MenuItem> menus = new ArrayList<MenuItem>();
		String[] titles = new String[6];

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU WHERE CODE_MENU = '"+idProduct+"'",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				titles[0] = c.getString(2);
				titles[1] = c.getString(3);
				titles[2] = c.getString(4);
				titles[3] = c.getString(5);
				titles[4] = c.getString(6);
				titles[5] = c.getString(7);
			} while (c.moveToNext());
		}
		c.close();

		for (int i = 0; i < titles.length; i++) {
			ArrayList<MenuDet> listMenuDets = (ArrayList<MenuDet>) new MenuDetCrud(db).findByMenu(idProduct,i);

			if(listMenuDets.size() != 0){
				menus.add(new MenuItem(titles[i], listMenuDets));
			} 
		}

		return menus.size();
	}

	public ArrayList<String> getTitlesMenu(String idProduct) {

		ArrayList<String> listTitles = new ArrayList<String>();

		String[] titles = new String[6];

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU WHERE CODE_MENU = '"+idProduct+"'",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				titles[0] = c.getString(2);
				titles[1] = c.getString(3);
				titles[2] = c.getString(4);
				titles[3] = c.getString(5);
				titles[4] = c.getString(6);
				titles[5] = c.getString(7);
			} while (c.moveToNext());
		}
		c.close();

		for (int i = 0; i < titles.length; i++) {
			ArrayList<MenuDet> listMenuDets = (ArrayList<MenuDet>) new MenuDetCrud(db).findByMenu(idProduct, i+1);
			
			if(listMenuDets.size() != 0){
				listTitles.add(titles[i]);
			} 
		}

		return listTitles;
	}
	
	public ArrayList<Integer> getNumPlatos(String idProduct) {

		ArrayList<Integer> listTitles = new ArrayList<Integer>();

		List<MenuItem> menus = new ArrayList<MenuItem>();
		Integer[] titles = new Integer[6];

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MENU WHERE CODE_MENU = '"+idProduct+"'",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				titles[0] = 1;
				titles[1] = 2;
				titles[2] = 3;
				titles[3] = 4;
				titles[4] = 5;
				titles[5] = 6;
			} while (c.moveToNext());
		}
		c.close();

		for (int i = 0; i < titles.length; i++) {
			ArrayList<MenuDet> listMenuDets = (ArrayList<MenuDet>) new MenuDetCrud(db).findByMenu(idProduct, i+1);

			if(listMenuDets.size() != 0){
				listTitles.add(titles[i]);
			} 
		}

		return listTitles;
	}

	@Override
	public boolean insert(List<Menu> listObjects) {
		// TODO Auto-generated method stub
		return false;
	}
}
