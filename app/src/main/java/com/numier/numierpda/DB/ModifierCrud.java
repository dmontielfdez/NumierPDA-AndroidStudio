package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.numier.numierpda.Controllers.NumierApi;
import com.numier.numierpda.Models.Modifier;


public class ModifierCrud implements Crud<Modifier> {

	// Atributos
	private Database db;

	// Constructor
	public ModifierCrud(Database db) {
		this.db = db;
	}

	// Inserción de datos
	@Override
	public boolean insert(List<Modifier> listObjects) {

		try {
			for (Modifier c : listObjects) {
				ContentValues values = new ContentValues();
				values.put("ID_MODIFIER", c.getId());
				values.put("NAME", c.getName());
				values.put("PRICE", c.getPrice());
				values.put("GRUPO", c.getGrupo());
				
				NumierApi.incrementProgress();

				db.getWritableDatabase().insert("MODIFIER", null, values);

			}
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Modifier> getAllGrupo(String grupo) {

		List<Modifier> modifiers = new ArrayList<Modifier>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM MODIFIER WHERE GRUPO = '' OR GRUPO = '"+grupo+"' ORDER BY GRUPO DESC, NAME", null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				modifiers.add(new Modifier(c.getInt(0), c.getString(1), c.getDouble(2), false, c.getString(3)));
			} while (c.moveToNext());
		}
		c.close();
		

		return modifiers;
	}

	public Modifier findById(int idModifier) {

		Modifier modifier = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * FROM MODIFIER WHERE ID_MODIFIER=" + idModifier,
				null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				modifier = new Modifier(c.getInt(0), c.getString(1),c.getDouble(2), false, c.getString(3));
			} while (c.moveToNext());
		}
		c.close();

		return modifier;
	}

	@Override
	public List<Modifier> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
