package com.example.earthquake;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeListFragment extends Fragment {
    private ArrayList<Earthquake> mEarthquakes =
            new ArrayList<Earthquake>();
    private RecyclerView mRecyclerView;
    private EarthQuakeAdapter mEarthquakeAdapter = new EarthQuakeAdapter(mEarthquakes);
    public EarthquakeListFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView = (RecyclerView) view;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mEarthquakeAdapter);
    }

    public void setEarthquakes(List<Earthquake> earthquakes) {
        for (Earthquake earthquake: earthquakes) {
            if (!mEarthquakes.contains(earthquake)) {
                mEarthquakes.add(earthquake);
                mEarthquakeAdapter
                        .notifyItemInserted(mEarthquakes.indexOf(earthquake));
            }
        }
    }
}
