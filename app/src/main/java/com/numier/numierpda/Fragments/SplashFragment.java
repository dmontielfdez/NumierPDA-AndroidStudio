package com.numier.numierpda.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numier.numierpda.Controllers.Init;
import com.numier.numierpda.R;

public class SplashFragment extends Fragment {


    public SplashFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        new Init(getActivity()).execute();
    }



}
