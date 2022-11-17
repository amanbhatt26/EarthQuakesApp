package com.example.earthquake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquake.databinding.ListItemEarthquakeBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeAdapter.Viewholder> {
    private final List<Earthquake> mEarthquakeList;
    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);
    private static final NumberFormat MAGNITUDE_FORMAT =
            new DecimalFormat("0.0");
    public EarthQuakeAdapter(List<Earthquake> mEarthquakeList) {
        this.mEarthquakeList = mEarthquakeList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemEarthquakeBinding binding =
                ListItemEarthquakeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Earthquake earthquake = mEarthquakeList.get(position);
        holder.binding.setEarthquake(earthquake);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mEarthquakeList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public ListItemEarthquakeBinding binding;
        public Viewholder(ListItemEarthquakeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setTimeformat(TIME_FORMAT);
            binding.setMagnitudeformat(MAGNITUDE_FORMAT);
        }

    }
}
