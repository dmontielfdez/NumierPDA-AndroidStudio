package com.numier.numierpda.DB;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.numier.numierpda.Models.Connection;

import java.util.ArrayList;
import java.util.List;


public class ConnectionsCrud{

	private Database db;

	public ConnectionsCrud(Database db) {
		this.db = db;
	}

	public long insertC(Connection c) {
		
		long id = 0;

		try {
				ContentValues values = new ContentValues();
				values.put("NAME", c.getName());
				values.put("URL", c.getUrl());
				values.put("KEY", c.getKey());
				values.put("OPERARIO", c.getOperario());
				values.put("RATE", c.getRate());
				values.put("TERMINAL", c.getTerminal());

				id = db.getWritableDatabase().insert("CONNECTIONS", null, values);

			
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return id;
		}
		return id;
	}

	public List<Connection> getAll() {

		List<Connection> connections = new ArrayList<Connection>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM CONNECTIONS", null);

		if (c.getCount() != 0) {

			c.moveToFirst();
			do {
				connections.add(new Connection(
						c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getString(3),
						c.getString(4),
						c.getString(5),
						c.getString(6)));						
			} while (c.moveToNext());
		}
		c.close();

		return connections;
	}

	
	public int deleteConnection(String id) {
			
		int result = db.getWritableDatabase().delete("CONNECTIONS", "ID_CONNECTION="+id, null);
		
		return result;
		
	}

	
	public String getNameConnection (String id){

		String datos = new String();

		Connection c = null;

		Cursor puntero = db.getReadableDatabase().rawQuery("SELECT * FROM CONNECTIONS WHERE ID_CONNECTION = '"+id+"'", null);

		if(puntero.getCount() != 0){
			puntero.moveToFirst();
			c = new Connection(
					puntero.getInt(0),
					puntero.getString(1),
					puntero.getString(2),
					puntero.getString(3),
					puntero.getString(4),
					puntero.getString(5),
					puntero.getString(6));

			datos = c.getName();			
		}		

		puntero.close();

		return datos;
	}


}
