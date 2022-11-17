package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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

        mEarthquakeListFragment.setEarthquakes(dummyQuakes);
    }
}