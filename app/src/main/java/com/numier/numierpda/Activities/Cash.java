package com.numier.numierpda.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.numier.numierpda.Adapters.TabAdapter;
import com.numier.numierpda.Fragments.CashFragment;
import com.numier.numierpda.Fragments.TicketFragment;
import com.numier.numierpda.R;

public class Cash extends AppCompatActivity {

    int tabSelected;
    int idHeader;
    int numBill;
    String nameBill;
    static CashFragment cashFragment;
    static TicketFragment ticketFragment;

    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        Intent intent = getIntent();
        idHeader = intent.getIntExtra("idHeader", 0);
        numBill = intent.getIntExtra("numBill", 0);
        nameBill = intent.getStringExtra("nameBill");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_detail);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Si desliza hacia la izquierda en el gridview recupera las categorias
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            if (viewPager.getCurrentItem() == 0) {
                                if (x2 > x1) {
                                    CashFragment.getCategories(CashFragment.superGrupoSelect);
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        cashFragment = new CashFragment(idHeader, numBill, nameBill);
        adapter.addFragment(cashFragment, "Caja");

        ticketFragment = new TicketFragment();
        adapter.addFragment(ticketFragment, "Ticket");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

    }

    public static TicketFragment getTicketFragment() {
        return ticketFragment;
    }

    public static CashFragment getCashFragment() {
        return cashFragment;
    }


}
