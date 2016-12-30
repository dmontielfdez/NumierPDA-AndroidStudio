package com.numier.numierpda.DB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.numier.numierpda.Controllers.NumierApi;
import com.numier.numierpda.Models.Worker;


public class WorkerCrud implements Crud<Worker> {

	private Database db;

	public WorkerCrud(Database db) {
		this.db = db;
	}

	@Override
	public boolean insert(List<Worker> listObjects) {

		try {
			for (Worker e : listObjects) {
				ContentValues values = new ContentValues();
				values.put("ID_WORKER", e.getId());
				values.put("PASS", e.getOperarioPassword());
				values.put("NAME", e.getName());

				db.getWritableDatabase().insert("WORKER", null, values);

			}
		} catch (SQLiteException sqlIo) {
			sqlIo.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Worker> getAll() {

		List<Worker> workers = new ArrayList<Worker>();

		Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM WORKER", null);

		if (c.getCount() != 0) {

			c.moveToFirst();
			do {
				workers.add(new Worker(
						c.getString(NumierApi.__WORKER_ID),
						c.getString(NumierApi.__WORKER_PASS),
						c.getString(NumierApi.__WORKER_NAME)));
			} while (c.moveToNext());
		}
		c.close();

		return workers;
	}


	public String[] getPwd (String id){

		String[] datos = new String[2];

		Worker empleadoBuscado = null;

		Cursor puntero = db.getReadableDatabase().rawQuery("SELECT * FROM WORKER WHERE ID_WORKER = '"+id+"'", null);
		Log.d("sql", "SELECT * FROM WORKER WHERE ID_WORKER = "+id);

		if(puntero.getCount() != 0){
			puntero.moveToFirst();
			empleadoBuscado = new Worker(
					puntero.getString(NumierApi.__WORKER_ID),
					puntero.getString(NumierApi.__WORKER_PASS),
					puntero.getString(NumierApi.__WORKER_NAME)
					);

			datos[0] = empleadoBuscado.getName();
			datos[1] = empleadoBuscado.getOperarioPassword();			
		}		

		puntero.close();

		return datos;
	}


}
