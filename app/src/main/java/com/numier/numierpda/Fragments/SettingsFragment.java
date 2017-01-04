package com.numier.numierpda.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numier.numierpda.Controllers.Init;
import com.numier.numierpda.DB.Database;
import com.numier.numierpda.DB.WorkerCrud;
import com.numier.numierpda.Models.Worker;
import com.numier.numierpda.R;

import java.util.List;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        Database db = new Database(getActivity());

        // No deja cambiar la ip del servidor
        EditTextPreference url = (EditTextPreference) findPreference("url");
        url.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return false;
            }
        });

        // Añado los operarios
        final ListPreference listWorkers = (ListPreference) findPreference("worker");

        List<String> names = new WorkerCrud(db).getAllNames();
        CharSequence[] entries = names.toArray(new CharSequence[names.size()]);
        listWorkers.setEntries(entries);

        List<String> codes = new WorkerCrud(db).getAllCodes();
        CharSequence[] entryValues = codes.toArray(new CharSequence[codes.size()]);
        listWorkers.setEntryValues(entryValues);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                    Preference singlePref = preferenceGroup.getPreference(j);
                    updatePreference(singlePref, singlePref.getKey());
                }
            } else {
                updatePreference(preference, preference.getKey());
            }
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(findPreference(key), key);

    }

    private void updatePreference(Preference preference, String key) {
        if (preference == null) return;
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setSummary(listPreference.getEntry());
            return;
        }
        if(key != null){
            if(!key.equals("logout") && !key.equals("check_version") && !key.equals("reload_data")){
                SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();
                preference.setSummary(sharedPrefs.getString(key, ""));
            }
        }


    }





}
