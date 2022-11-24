package com.example.earthquake;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeListFragment extends Fragment {
    private static final String TAG = "EarthquakeListFragment";
    private ArrayList<Earthquake> mEarthquakes =
            new ArrayList<Earthquake>();
    private RecyclerView mRecyclerView;
    private EarthQuakeAdapter mEarthquakeAdapter = new EarthQuakeAdapter(mEarthquakes);
    protected EarthquakeViewModel earthquakeViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    public EarthquakeListFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called for the fragment");

        earthquakeViewModel = new ViewModelProvider(getActivity()).get(EarthquakeViewModel.class);
        earthquakeViewModel.getEarthquakes().observe(this, new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(List<Earthquake> earthquakes) {
                if(earthquakes!=null){
                    setEarthquakes(earthquakes);
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mEarthquakeAdapter);

        swipeRefreshLayout.setOnRefreshListener(new
                                                         SwipeRefreshLayout.OnRefreshListener() {
                                                             @Override
                                                             public void onRefresh() {
                                                                 updateEarthquakes();
                                                             }
                                                         });
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentRefreshRequested();
    }
    private OnListFragmentInteractionListener mListener;
    @Override
    public void onAttach(Context context) {
        // the context is actually the parent object
        super.onAttach(context);
        mListener = (OnListFragmentInteractionListener) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    protected void updateEarthquakes() {
        if (mListener != null)
            mListener.onListFragmentRefreshRequested();
    }
    public void setEarthquakes(List<Earthquake> earthquakes) {
        mEarthquakes.clear();
        mEarthquakeAdapter.notifyDataSetChanged();
        for (Earthquake earthquake: earthquakes) {
            if (!mEarthquakes.contains(earthquake)) {
                mEarthquakes.add(earthquake);
                mEarthquakeAdapter
                        .notifyItemInserted(mEarthquakes.indexOf(earthquake));
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
