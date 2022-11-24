package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EarthquakeListFragment.OnListFragmentInteractionListener  {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    EarthquakeListFragment mEarthquakeListFragment;
    EarthquakeViewModel earthquakeViewModel;
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

        earthquakeViewModel = new ViewModelProvider(this).get(EarthquakeViewModel.class);


    }

    @Override
    public void onListFragmentRefreshRequested() {
        updateEarthquakes();
    }
    private void updateEarthquakes() {
// Request the View Model update the earthquakes from the USGS feed.
        earthquakeViewModel.loadData();
    }
}