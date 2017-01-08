package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Models.Category;


public class CategoryCrud {

	private Database db;

	public CategoryCrud(Database db) {
		this.db = db;
	}

	public boolean insert(List<Category> listObjects) {

		try {
			for (Category c : listObjects) {
				ContentValues values = new ContentValues();
				values.put("ID_CATEGORY", c.getId());
				values.put("CODE", c.getCode());
				values.put("NAME", c.getName());

				db.getWritableDatabase().insert("CATEGORY", null, values);

			}
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}


	public List<Category> getAll() {

		List<Category> categories = new ArrayList<Category>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM CATEGORY", null);

		if (c.moveToFirst()) {
			do {
				categories.add(new Category(c.getString(0), c.getString(1)));
			} while (c.moveToNext());
		}
		c.close();

		return categories;
	}
	

	public List<Category> getSuperGrupo(int supergrupo) {
		String xcond = "";
		
		switch (supergrupo) {
		case 1:
			xcond = "(code  = '0' or code  = '1' or code  = '2' or code  = '3' or code  = '4' or code  = '5' or code  = '6' or code  = '7' or code  = '8' or code  = '9')";
			break;
		
		case 2:
			xcond = "(code  = 'a' or code  = 'b' or code  = 'c' or code  = 'd' or code  = 'e' or code  = 'f' or code  = 'g' or code  = 'h' or code  = 'i' or code  = 'j')";
			break;

		case 3:
			xcond = "(code  = 'k' or code  = 'l' or code  = 'm' or code  = 'n' or code  = 'o' or code  = 'p' or code  = 'q' or code  = 'r' or code  = 's' or code  = 't')";
			break;

		case 4:
			xcond = "(code  = 'u' or code  = 'v' or code  = 'w' or code  = 'x' or code  = 'y' or code  = 'z' or code  = 'á' or code  = 'é' or code  = 'í' or code  = 'ó')";
			break;

		default:
			break;
	}
		
		List<Category> categories = new ArrayList<Category>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM CATEGORY where "+xcond, null);

		if (c.moveToFirst()) {
			do {
				categories.add(new Category(c.getString(0), c.getString(1)));
			} while (c.moveToNext());
		}
		c.close();

		return categories;
	}
}
