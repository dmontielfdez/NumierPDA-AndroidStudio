package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Models.ProductSubproduct;


public class ProductSubproductCrud {

	private Database db;

	public ProductSubproductCrud(Database db) {
		this.db = db;
	}

	public int insert(ProductSubproduct ps) {
		int c = 0;
		try {

				ContentValues values = new ContentValues();
				values.put("ID_SUBPRODUCT", ps.getIdSubproduct());
				values.put("ID_PRODUCT", ps.getIdProduct());
				values.put("OPCIONAL", ps.getOpcional());
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

		if (c.moveToFirst()) {
			do {
				productSubproducts.add(new ProductSubproduct(c.getString(1), c.getInt(0), c.getInt(2), c.getDouble(3), c.getInt(4), c.getString(5)));
			} while (c.moveToNext());
		}
		c.close();

		return productSubproducts;
	}

	public List<ProductSubproduct> findSubproduct(String idProduct) {

		List<ProductSubproduct> productSubproducts = new ArrayList<ProductSubproduct>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM PRODUCTSUBPRODUCT WHERE ID_PRODUCT=\"" + idProduct + "\" order by orden", null);
		

		if (c.moveToFirst()) {
			do {
				productSubproducts.add(new ProductSubproduct(c.getString(1), c.getInt(0), c.getInt(2), c.getDouble(3), c.getInt(4), c.getString(5)));
			} while (c.moveToNext());
		}
		c.close();

		return productSubproducts;
	}

	public ProductSubproduct findSubProductByid(String idSubProduct, String idProduct) {

		ProductSubproduct productSubproduct = new ProductSubproduct();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM PRODUCTSUBPRODUCT WHERE ID_SUBPRODUCT=" + idSubProduct+" AND ID_PRODUCT='" + idProduct+"' order by orden", null);

		if (c.moveToFirst()) {
			productSubproduct = new ProductSubproduct(c.getString(1),c.getInt(0), c.getInt(2), c.getDouble(3), c.getInt(4), c.getString(5));
		}
		c.close();

		return productSubproduct;
	}
	
	
}
