package com.numier.numierpda.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



public class PreferencesTools {


	public static boolean savePreferences(Activity context, String key, String value) {

		SharedPreferences prefs = context.getSharedPreferences("NumierPDA", Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		return editor.commit();
	}


	public static boolean deletePreferences(Activity context,
			String namePreferences) {

		SharedPreferences prefs = context.getSharedPreferences(namePreferences,
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		return editor.commit();
	}


	public static String getValueOfPreferences(Activity context, String key) {
		
		SharedPreferences prefs = context.getSharedPreferences("NumierPDA",	Context.MODE_PRIVATE);
		return prefs.getString(key, "");
		
	}
}
