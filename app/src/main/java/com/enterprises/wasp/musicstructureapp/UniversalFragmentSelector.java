package com.enterprises.wasp.musicstructureapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class UniversalFragmentSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_container);
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        // Set the adapter onto the view pager
        try {
            viewPager.setAdapter(adapter);
        } catch (NullPointerException npe) {
            Log.e("viewPager", "Caught NullPointerException: " + npe);
        }

        try {
            TabLayout tabLayout = findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        } catch (NullPointerException npe) {
            Log.e("tabLayout", "Caught NullPointerException: " + npe);
        }
    }
}


