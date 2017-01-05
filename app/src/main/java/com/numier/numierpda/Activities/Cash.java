package com.numier.numierpda.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.numier.numierpda.Adapters.TabAdapter;
import com.numier.numierpda.Fragments.CashFragment;
import com.numier.numierpda.Fragments.TicketFragment;
import com.numier.numierpda.R;

public class Cash extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;
    private TabAdapter adapter;
    int tabSelected;
    int idHeader;
    int numBill;
    String nameBill;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        viewPager = (ViewPager) findViewById(R.id.viewpager_detail);
        setupViewPager(viewPager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new TabAdapter(getSupportFragmentManager());

        CashFragment cf = new CashFragment();
        adapter.addFragment(cf, "Caja");

        TicketFragment tf = new TicketFragment();
        adapter.addFragment(tf, "Ticket");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

    }
}
