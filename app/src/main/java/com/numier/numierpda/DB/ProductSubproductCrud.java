package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Controllers.NumierApi;
import com.numier.numierpda.Models.ProductSubproduct;


public class ProductSubproductCrud {

	// Atributos
	private Database db;

	// Constructor
	public ProductSubproductCrud(Database db) {
		this.db = db;
	}

	public int insert(ProductSubproduct ps) {
		int c = 0;
		try {

				ContentValues values = new ContentValues();
				values.put("ID_SUBPRODUCT", ps.getIdSubproduct());
				values.put("ID_PRODUCT", ps.getIdProduct());
				values.put("INCLUDED", ps.getIncluded());
				values.put("PRICE", ps.getPrice());
				values.put("ORDEN", ps.getOrden());
				values.put("TYPE", ps.getType());

			long id = db.getWritableDatabase().insert("PRODUCTSUBPRODUCT", null, values);
			c = (int)id;

		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return 0;
		}
		return c;
	}

	public List<ProductSubproduct> getAll() {

		List<ProductSubproduct> productSubproducts = new ArrayList<ProductSubproduct>();

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * FROM PRODUCTSUBPRODUCT", null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya mas registros
			do {
				productSubproducts.add(new ProductSubproduct(c.getString(1), c.getInt(0), c.getInt(2), c.getDouble(3), c.getInt(4), c.getString(5)));
			} while (c.moveToNext());
		}
		c.close();

		return productSubproducts;
	}

	public List<ProductSubproduct> findSubproduct(String idProduct) {

		List<ProductSubproduct> productSubproducts = new ArrayList<ProductSubproduct>();

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * FROM PRODUCTSUBPRODUCT WHERE ID_PRODUCT=\""
						+ idProduct + "\" order by orden", null);
		
		
		Log.d("sql", "SELECT * FROM PRODUCTSUBPRODUCT WHERE ID_PRODUCT=\""+ idProduct + "\" order by ORDEN");

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya m�s registros
			do {
				productSubproducts.add(new ProductSubproduct(c.getString(1), c.getInt(0), c.getInt(2), c.getDouble(3), c.getInt(4), c.getString(5)));
			} while (c.moveToNext());
		}
		c.close();

		return productSubproducts;
	}

	public ProductSubproduct findSubProductByid(String idSubProduct) {

		ProductSubproduct productSubproduct = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * FROM PRODUCTSUBPRODUCT WHERE ID_SUBPRODUCT="
						+ idSubProduct+" order by orden", null);

		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya m�s registros
			productSubproduct = new ProductSubproduct(c.getString(1),c.getInt(0), c.getInt(2), c.getDouble(3), c.getInt(4), c.getString(5));
		}
		c.close();

		return productSubproduct;
	}
	
	
}
