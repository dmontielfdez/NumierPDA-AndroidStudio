package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.numier.numierpda.Models.Subproduct;


public class SubproductCrud implements Crud<Subproduct> {

	// Atributos
	private Database db;

	// Constructor
	public SubproductCrud(Database db) {
		this.db = db;
	}

	// Inserción de datos
	@Override
	public boolean insert(List<Subproduct> listObjects) {

		try {
			for (Subproduct c : listObjects) {
				ContentValues values = new ContentValues();
				values.put("ID_SUBPRODUCT", c.getId());
				values.put("NAME", c.getName());
				values.put("PRICE", c.getPrice());

				db.getWritableDatabase().insert("SUBPRODUCT", null, values);

			}
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Subproduct> getAll() {

		List<Subproduct> subproducts = new ArrayList<Subproduct>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM SUBPRODUCT",
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				subproducts.add(new Subproduct(c.getInt(0), c.getString(1), c
						.getDouble(2)));
			} while (c.moveToNext());
		}
		c.close();

		return subproducts;
	}

	public Subproduct findById(int idSubProduct) {

		Subproduct subproduct = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * FROM SUBPRODUCT WHERE ID_SUBPRODUCT=" + idSubProduct,
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				subproduct = new Subproduct(c.getInt(0), c.getString(1),
						c.getDouble(2));
			} while (c.moveToNext());
		}
		c.close();

		return subproduct;
	}
}
