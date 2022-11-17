package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    EarthquakeListFragment mEarthquakeListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null){
            mEarthquakeListFragment = new EarthquakeListFragment();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(R.id.main_activity_frame, mEarthquakeListFragment, TAG_LIST_FRAGMENT);
            ft.commitNow();
        }else{
            mEarthquakeListFragment =
                    (EarthquakeListFragment)mFragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT);
        }

        Date now = Calendar.getInstance().getTime();
        List<Earthquake> dummyQuakes = new ArrayList<Earthquake>(0);
        dummyQuakes.add(new Earthquake("0", now, "San jose", null, 6.0, null));
        dummyQuakes.add(new Earthquake("1", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("2", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("3", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("4", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("5", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("6", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("7", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("8", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("9", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("10", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("11", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("12", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("13", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("14", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("15", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("16", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("17", now, "LA", null, 8.0, null));
        dummyQuakes.add(new Earthquake("18", now, "LAs", null, 8.0, null));
        dummyQuakes.add(new Earthquake("19", now, "LAs", null, 8.0, null));


        mEarthquakeListFragment.setEarthquakes(dummyQuakes);
    }
}