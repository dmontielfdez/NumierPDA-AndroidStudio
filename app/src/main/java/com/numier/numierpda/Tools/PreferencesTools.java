package com.numier.numierpda.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PreferencesTools {


	public static boolean savePreferences(Activity context, String key, String value) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

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
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(key, "");
		
	}
}
